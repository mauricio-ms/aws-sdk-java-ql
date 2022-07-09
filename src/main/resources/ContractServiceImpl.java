package com.beatstars.contract.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.beatstars.account.client.api.account.AccountClient;
import com.beatstars.account.client.dto.account.AccountType;
import com.beatstars.common.App;
import com.beatstars.common.app.exception.ServiceException;
import com.beatstars.common.dao.TableScanner;
import com.beatstars.common.filters.SearchFilters;
import com.beatstars.common.filters.SearchFilters.SearchFiltersBuilder;
import com.beatstars.common.model.ItemDao;
import com.beatstars.common.model.Pair;
import com.beatstars.common.model.Response;
import com.beatstars.common.model.item.Sort;
import com.beatstars.common.service.ResponseService;
import com.beatstars.common.service.UtilService;
import com.beatstars.common.service.ValidatorService;
import com.beatstars.contract.client.dto.ContractCategory;
import com.beatstars.contract.client.dto.ContractDeliverable;
import com.beatstars.contract.client.dto.ContractDownloaderRequirement;
import com.beatstars.contract.client.dto.ContractDto;
import com.beatstars.contract.client.dto.ContractId;
import com.beatstars.contract.client.dto.ContractStatus;
import com.beatstars.contract.client.dto.ContractType;
import com.beatstars.contract.client.dto.ContractVariable;
import com.beatstars.contract.client.dto.ContractVersion;
import com.beatstars.contract.client.dto.SocialMedia;
import com.beatstars.contract.client.utils.DeliverablesTranslator;
import com.beatstars.contract.client.utils.DeliverablesTranslatorImpl;
import com.beatstars.contract.data.dao.ContractDao;
import com.beatstars.contract.data.dao.FeaturedContractDao;
import com.beatstars.contract.data.dao.PageScanner;
import com.beatstars.contract.data.dto.ResetContractTemplate;
import com.beatstars.contract.data.model.Contract;
import com.beatstars.contract.data.model.ContractAttachment;
import com.beatstars.contract.data.model.FeaturedContract;
import com.beatstars.contract.data.model.exception.MessageCode;
import com.beatstars.contract.data.model.history.ContractTemplate;
import com.beatstars.contract.service.preset.ContractPresetFactory;
import com.beatstars.legacy.client.api.LegacyAsyncClient;
import com.beatstars.legacy.client.api.license.LicenseLegacyClient;
import com.beatstars.subscription.client.dto.SubscriptionPlan;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.typesense.api.Client;

@Service
@Validated
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
class ContractServiceImpl extends App implements ContractService {

    private final ContractDao contractDao;
    private final FeaturedContractDao featuredContractDao;

    private final ContractPresetFactory contractPresetFactory;
    private final ContractAttachmentService contractAttachmentService;
    private final AccountClient accountClient;

    private final ResponseService responseService;
    private final ModelMapper modelMapper;
    private final UtilService utilService;
    private final ValidatorService<Contract> validatorService;

    private final LicenseLegacyClient legacyClient;
    private final LegacyAsyncClient legacyAsyncClient;
    private final Client typeSenseClient;

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    private final ContractTemplateService contractTemplateService;
    private final ContractHistoryService contractHistoryService;

    @Value("${api.contract.sns}")
    private String snsTopic;

    private int primitive;

    @Override
    public String getPrefix() {
        return ContractId.getPrefix();
    }

    @Override
    public ItemDao<Contract> getItemDao() {
        return contractDao;
    }

    @Override
    public ValidatorService<Contract> getValidatorService() {
        return validatorService;
    }

    @Override
    public ContractStatus getDefaultStatus() {
        return ContractStatus.DRAFT;
    }

    @Override
    public ContractStatus getArchivedStatus() {
        return ContractStatus.ARCHIVED;
    }

    @Override
    public AccountClient getAccountClient() {
        return accountClient;
    }

    @Override
    public long limitDefault() {
        return 0;
    }

    @Override
    public void init(Contract contract) {
        if (contract.getCategory() == null) {
            contract.setCategory(ContractCategory.REGULAR);
        }

        if (isV2IdRequired(contract)) {
            Response<Long> response = legacyClient.add(contract.getType().name(), getUserId(contract.getMemberId()));
            contract.setV2Id(response.getBody());
        }

        if (contract.getStatus() == null) {
            contract.setStatus(ContractStatus.DRAFT);
        }

        ContractVersion version = Boolean.TRUE.equals(contract.getDisableLegacyNotification()) ?
                ContractVersion.LEGACY : ContractVersion.CURRENT;
        contract.setVersion(version);

        if (contract.getEnabled() == null) {
            contract.setEnabled(false);
        }

        if (contract.getSort() == null) {
            contract.setSort(Sort.builder().build());
        }

        contract.setPk(generatePk(contract));

        fillTemplateIfEmpty(contract);

        contract.setId(getId(contract));
        handleTemplate(contract);
    }

    private void fillTemplateIfEmpty(Contract contract) {
        if (contract.getTemplateId() == null && contract.getTemplate() == null) {
            setTemplateDefault(contract);
        }
    }

    private void handleTemplate(Contract contract) {
        if (contract.getTemplate() == null) {
            return;
        }
        setTemplateId(contract);
        contract.setTemplate(null);
    }

    @Override
    public Contract update(String id, Contract item, @Nullable String memberId) {
        Contract target = get(id, memberId);

        isWriteAuthorized(target, memberId);

        // TODO: Revisit once we figure out why `BeanUtils.copyProperties` does not deeply clone target x source.
        if (nonNull(item.getVariable()) && nonNull(target.getVariable())) {
            ContractVariable contractVariable = target.getVariable();
            BeanUtils.copyProperties(item.getVariable(), contractVariable, utilService.getNullPropertyNames(item.getVariable()));
            target.setVariable(contractVariable);
            item.setVariable(null);
        }

        BeanUtils.copyProperties(item, target, ArrayUtils.add(utilService.getNullPropertyNames(item), "version"));

        item = save(target);

        return item;
    }

    @Override
    public void preSave(Contract contract) {
        ContractService.super.preSave(contract);

        ensureContractVersion(contract);

        fillFreeRequirement(contract);
        fillVariableIfNull(contract);
        fillTemplateIfEmpty(contract);

        validate(contract);

        contract.setTime(Instant.now().toEpochMilli());
        contract.setSk(generateSk(contract));
        if (contract.getTemplate() != null) {
            contract.setHash(utilService.getMd5(contract.getTemplate()));
        }
        handleTemplate(contract);

        if (contract.getSocialMedias() != null && contract.getSocialMedias().isEmpty()) {
            contract.setSocialMedias(null);
        }

        fixOldContractDeliverables(contract);
        setWeight(contract);
        upsertTypeSenseDocumentIfNeeded(contract);
        contract.setUpdateAttachmentsInLegacy(shouldUpdateAttachmentsInLegacy(contract));
    }

    private boolean shouldUpdateAttachmentsInLegacy(Contract contract) {
        return Optional.ofNullable(contractDao.getById(contract.getId()))
                .map(savedContract -> {
                    ContractDownloaderRequirement oldDownloaderRequirement = savedContract.getDownloaderRequirement();
                    return !Objects.equals(oldDownloaderRequirement, contract.getDownloaderRequirement());
                })
                .orElse(false);
    }

    private void setTemplateId(Contract contract) {
        String previousTemplateId = contract.getTemplateId();
        ContractTemplate contractTemplate = contractTemplateService.createIfTemplateChanged(contract.getType(), contract.getCategory(), contract.getTemplate());
        contract.setTemplateId(contractTemplate.getId());

        if (contractHistoryService.shouldCreateEntry(previousTemplateId, contractTemplate.getId())) {
            contractHistoryService.createEntry(contract.getId(), contractTemplate.getId());
        }
    }

    // TODO Only to the next release for the users that request the data before the migration is run
    private boolean fixOldContractDeliverables(Contract contract) {
        if (contract.getDeliverables() == null) {
            return false;
        }

        Predicate<Set<ContractDeliverable>> hasExactDeliverables = deliverables -> CollectionUtils.isEqualCollection(contract.getDeliverables(), deliverables);

        if (hasExactDeliverables.test(Set.of(ContractDeliverable.MP3_TAGGED, ContractDeliverable.WAV))) {
            contract.setDeliverables(Set.of(ContractDeliverable.MP3, ContractDeliverable.WAV));
            return true;
        } else if (hasExactDeliverables.test(Set.of(ContractDeliverable.MP3_TAGGED, ContractDeliverable.WAV, ContractDeliverable.STEMS))) {
            contract.setDeliverables(Set.of(ContractDeliverable.MP3, ContractDeliverable.WAV, ContractDeliverable.STEMS));
            return true;
        } else if (hasExactDeliverables.test(Set.of(ContractDeliverable.MP3, ContractDeliverable.STEMS)) ||
                hasExactDeliverables.test(Set.of(ContractDeliverable.MP3_TAGGED, ContractDeliverable.STEMS)) ||
                hasExactDeliverables.test(Set.of(ContractDeliverable.MP3, ContractDeliverable.MP3_TAGGED, ContractDeliverable.STEMS))) {
            contract.setDeliverables(Set.of(ContractDeliverable.MP3, ContractDeliverable.WAV, ContractDeliverable.STEMS));
            return true;
        }

        return false;
    }

    private void fillVariableIfNull(Contract contract) {
        boolean isMissingVariable = isNull(contract.getVariable());
        boolean isLegacyContract = nonNull(contract.getVersion()) && Objects.equals(contract.getVersion(), ContractVersion.LEGACY);
        if (isMissingVariable && !isLegacyContract) {
            ContractVariable contractVariable = contractPresetFactory.getPresetService(contract.getCategory()).getDefaultVariable();
            contract.setVariable(contractVariable);
        }
    }

    private void ensureContractVersion(Contract contract) {
        if (contract.getVersion() == null) {
            contract.setVersion(inferVersionFromOldContract(contract));
        }
    }

    private ContractVersion inferVersionFromOldContract(Contract contract) {
        List<Pair<ContractType, ContractCategory>> legacyConfig = List.of(
                Pair.of(ContractType.TRACK, ContractCategory.REGULAR),
                Pair.of(ContractType.TRACK, ContractCategory.EXCLUSIVE),
                Pair.of(ContractType.ALBUM, ContractCategory.REGULAR),
                Pair.of(ContractType.ALBUM, ContractCategory.EXCLUSIVE)
        );
        if (contractIsValidForConfig(contract, legacyConfig)) {
            return ContractVersion.LEGACY;
        }

        List<Pair<ContractType, ContractCategory>> currentConfig = List.of(
                Pair.of(ContractType.TRACK, ContractCategory.FREE),
                Pair.of(ContractType.TRACK, ContractCategory.SONG),
                Pair.of(ContractType.ALBUM, ContractCategory.FREE),
                Pair.of(ContractType.ALBUM, ContractCategory.SONG),
                Pair.of(ContractType.SOUNDKIT, ContractCategory.REGULAR),
                Pair.of(ContractType.SOUNDKIT, ContractCategory.FREE),
                Pair.of(ContractType.SERVICE, ContractCategory.REGULAR),
                Pair.of(ContractType.SERVICE, ContractCategory.FREE)
        );
        if (contractIsValidForConfig(contract, currentConfig)) {
            return ContractVersion.CURRENT;
        }

        logger.warn("The contract version cannot be inferred for contract {}", contract);
        return null;
    }

    private boolean contractIsValidForConfig(Contract contract, List<Pair<ContractType, ContractCategory>> config) {
        return config
                .stream()
                .anyMatch(e -> e.getKey().equals(contract.getType()) && e.getValue().equals(contract.getCategory()));
    }

    private void fillFreeRequirement(Contract contract) {
        boolean isFree = isFreeContract(contract.getCategory());
        if (isFree) {
            ContractDownloaderRequirement requirement = contract.getDownloaderRequirement();
            if (isNull(requirement)) {
                contract.setDownloaderRequirement(ContractDownloaderRequirement.ANONYMOUS);
            }
        }
    }

    private void setWeight(Contract contract) {
        int weight = 0;
        if (contract.getDeliverables() != null) {
            DeliverablesTranslator deliverablesTranslator = new DeliverablesTranslatorImpl();
            weight = deliverablesTranslator.encode(contract.getDeliverables());
        }
        contract.setWeight(weight);
    }

    private void validate(Contract contract) {
        if (Boolean.TRUE.equals(shouldValidate(contract))) {
            validatorService.isValidOnSave(contract);

            boolean isPublished = isPublished(contract);
            if (isPublished) {
                validatorService.isValidOnPublish(contract);
            }
        }

    }

    private Boolean shouldValidate(Contract contract) {
        return contract.shouldValidate();
    }

    @Override
    public void postSave(Contract contract) {
        ContractService.super.postSave(contract);
        final String contractId = contract.getId();
        final String memberId = contract.getMemberId();

        if (contract.getStatus().equals(ContractStatus.ARCHIVED)) {
            contract.setPk(generatePk(contract));
            getFeatured(contract.getMemberId())
                    .filter(c -> c.getId().equals(contractId))
                    .ifPresent(c -> featuredContractDao.deleteById(memberId));
        }

        notifyLegacy(contract);

        notify(contract);
    }

    private void notify(Contract contract) {
        if (!Boolean.TRUE.equals(contract.getDisableNotification())) {
            notificationMessagingTemplate.sendNotification(snsTopic, contract, contract.getType().getPrefix());
        }
    }

    @Override
    public void preArchive(Contract contract) {
        contract.setDisableLegacyNotification(false);
    }

    @Override
    public void postArchive(Contract contract) {
        String contractId = contract.getId();
        TableScanner.of(100, pageable -> contractAttachmentService.listByContractId(contractId, pageable))
                .forEach(attachment -> {
                    contractAttachmentService.detach(attachment.getContractId(), attachment.getItemId());
                });
    }

    @Override
    public Contract create(ContractType type, ContractCategory category, String memberId) {
        Contract contract = contractPresetFactory.create(type, category);
        contract.setMemberId(memberId);
        contract.setDisableLegacyNotification(false);
        return ContractService.super.add(contract);
    }

    @Override
    public void updateDeliverables(String contractId, Set<ContractDeliverable> contractDeliverables, @Nullable String callbackId, String memberId) {
        Contract contract = get(contractId, memberId);
        contract.setDeliverables(contractDeliverables);
        contract = save(contract);
        contractAttachmentService.flagIneligibleContractAttachments(callbackId, contract);
    }

    /**
     * Default contracts should be created already published.
     */
    private void createDefaultContract(ContractType type, ContractCategory category, String memberId, ContractVersion version) {
        Contract contract = contractPresetFactory.create(type, ContractStatus.PUBLISHED, category);
        contract.setMemberId(memberId);
        contract.setVersion(version);
        boolean disableLegacyNotification = switch (version) {
            case LEGACY -> true;
            case CURRENT -> false;
        };
        contract.setDisableLegacyNotification(disableLegacyNotification);
        if (shouldCreateDefaultContract(contract)) {
            ContractService.super.add(contract);
        }
    }

    private boolean shouldCreateDefaultContract(Contract contract) {
        boolean isSongContract = isSongContract(contract.getCategory());
        if (isSongContract) {
            String id = getId(contract);
            return !exists(id);
        }
        return true;
    }

    private boolean exists(String id) {
        return contractDao.existsById(id);
    }

    @Override
    public Contract publish(final String id, String memberId) {
        Contract contract = get(id, memberId);

        if (!isPublished(contract)) {
            checkContractLimit(memberId, contract);
        }

        contract.setStatus(ContractStatus.PUBLISHED);
        contract.setDisableLegacyNotification(false);
        contractAttachmentService.detachIneligibleContractAttachments(id);

        return save(contract);
    }

    @Override
    public Page<Contract> list(String memberId,
                               @Nullable ContractStatus status,
                               ContractType type,
                               ContractCategory category,
                               Pageable pageable) {
        return list(memberId, type, category, status, pageable);
    }

    private Page<Contract> list(String memberId,
                                ContractType type,
                                @Nullable ContractCategory category,
                                @Nullable ContractStatus status,
                                Pageable pageable) {
        return contractDao.getByPkAndSkStartingWith(
                generatePk(memberId, type),
                generateSk(category, status, null),
                pageable
        );
    }

    @Override
    public Page<Contract> listExceptForSomeCategories(String memberId, ContractType type, ContractStatus status, List<ContractCategory> categoriesExcluded, Pageable pageable) {
        return contractDao.getAllByPkAndStatusExceptForSomeCategories(generatePk(memberId, type), status, categoriesExcluded, pageable);
    }

    @Override
    public Page<Contract> listEnabledButFree(String memberId, Set<ContractType> types, ContractStatus status, Pageable pageable) {
        return contractDao.getAllEnabledButFreeByMemberId(memberId, types, status.toString(), pageable);
    }

    @Override
    public List<Contract> listByItemId(String itemId, String memberId, Boolean attachedOnly, @Nullable ContractCategory category) {

        if (Boolean.TRUE.equals(attachedOnly)) {
            return listAttachedContracts(itemId, category)
                    .stream()
                    .filter(c -> Boolean.TRUE.equals(c.getEnabled()))
                    .collect(Collectors.toList());
        }

        Page<Contract> page;
        Pageable pageable = PageRequest.of(0, 100);
        List<Contract> contracts = new ArrayList<>();

        do {
            page = getDefaultContractsForAttachment(itemId, memberId, category, null, pageable);
            contracts.addAll(page.getContent());
            pageable = page.nextPageable();
        } while (page.hasNext());

        return contracts;
    }

    @Override
    public Page<Contract> pageableListByItemId(String itemId,
                                               String memberId,
                                               Boolean attachedOnly,
                                               Boolean enabledOnly,
                                               @Nullable ContractCategory category,
                                               @Nullable Set<ContractDeliverable> deliverables,
                                               Pageable pageable) {
        if (Boolean.TRUE.equals(attachedOnly)) {
            return PageScanner.of(pageable, currentPageable -> {
                        Page<ContractAttachment> contractAttachmentPage = contractAttachmentService.listByItem(itemId, enabledOnly, currentPageable);
                        List<Contract> contracts = listByAttachment(contractAttachmentPage);

                        contracts = filterByPublished(contracts);
                        contracts = filterByCategory(contracts, itemId, category);

                        return new PageImpl<>(contracts, currentPageable, contractAttachmentPage.getTotalElements());
                    })
                    .scanUntilSatisfyRequestedPage()
                    .getResult();
        }

        return getDefaultContractsForAttachment(itemId, memberId, category, deliverables, pageable);
    }

    @Override
    public Page<Contract> listValidAttachedByItemId(String itemId, Pageable pageable) {
        return PageScanner.of(pageable, currentPageable -> {
                    Page<ContractAttachment> contractAttachmentPage = contractAttachmentService.listEnabledAndValidByItemId(itemId, currentPageable);
                    List<Contract> contracts = listByAttachment(contractAttachmentPage);
                    contracts = filterByPublished(contracts);
                    return new PageImpl<>(contracts, currentPageable, contractAttachmentPage.getTotalElements());
                })
                .scanUntilSatisfyRequestedPage()
                .getResult();
    }

    @Override
    public Contract feature(final String id, String memberId, boolean state) {
        Contract contract = get(id, memberId);
        contract.setDisableLegacyNotification(false);

        if (state) {
            if (!isPublished(contract)) {
                throw new RuntimeException(responseService.getMessage("INVALID_STATUS", contract.getStatus()));
            }

            featuredContractDao.save(
                    FeaturedContract.builder()
                            .id(memberId)
                            .contractId(id)
                            .build()
            );
        } else {
            featuredContractDao.deleteById(memberId);
        }

        String deduplicationId = String.format("%s-%d", contract.getId(), new Date().getTime());
        Map<String, Object> additionalHeaders = new HashMap<>();
        additionalHeaders.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, deduplicationId);
        notifyLegacy(contract, additionalHeaders);

        return contract;
    }

    @Override
    public Contract enable(final String id, String memberId, boolean state) {
        Contract contract = get(id, memberId);

        contract.setEnabled(state);

        return save(contract);
    }

    @Override
    public Contract restore(final String id, String memberId) {
        Contract contract = get(id, memberId);
        if (!getArchivedStatus().equals(contract.getStatus())) {
            throw new ServiceException(MessageCode.ITEM_NON_ARCHIVED_CANNOT_BE_RESTORED);
        }

        checkContractLimit(memberId, contract);

        contract.setStatus(ContractStatus.PUBLISHED);
        contract.setPk(generatePk(contract));
        contract.setDisableLegacyNotification(false);

        return save(contract);
    }

    private void checkContractLimit(@NotNull final String memberId, @NotNull final Contract contract) {
        if (isFreeContract(contract.getCategory())) {
            logger.trace("Free contract {} has no creation limit", contract.getId());
            return;
        }
        checkAllowedLimit(memberId, getTotalLimitPublishedContracts(contract, memberId));
    }

    private Long getTotalLimitPublishedContracts(final Contract currentContract, final String memberId) {

        List<Contract> contracts = new ArrayList<>();
        Page<Contract> page;
        Pageable pageable = PageRequest.of(0, 100);

        do {
            page = listExceptForSomeCategories(memberId, currentContract.getType(), ContractStatus.PUBLISHED, List.of(ContractCategory.FREE), pageable);
            contracts.addAll(page.getContent());
            pageable = page.nextPageable();
        } while (page.hasNext());

        if (contracts.isEmpty()) {
            return BigDecimal.ZERO.longValue();
        }

        Long publishedContracts = contracts.stream()
                .filter(contract -> !isFreeContract(contract.getCategory()))
                .filter(contract -> !isSongContract(contract.getCategory()))
                .count();

        if (isPublished(currentContract)) {
            publishedContracts--;
        }

        return publishedContracts;
    }

    private boolean isPublished(Contract contract) {
        return ContractStatus.PUBLISHED.equals(contract.getStatus());
    }

    @Override
    public String getFormattedTemplate(String id) {
        Optional<Contract> optional = get(id);
        if (optional.isPresent()) {
            Contract contract = optional.get();

            // TODO: Refactor formatted template using the new ContractVariable

            return contract.getTemplate();
        } else {
            return null;
        }
    }

    @Override
    public Optional<Contract> getFeatured(String memberId) {
        return featuredContractDao.findById(memberId).flatMap(featuredContract -> get(featuredContract.getContractId()));
    }

    @Override
    public String getId(Contract contract) {
        if (isV2IdRequired(contract)) {
            throw new RuntimeException(responseService.getMessage("INVALID_STATUS", contract.getStatus()));
        }
        String contractId = nonNull(contract.getV2Id()) ? contract.getV2Id().toString() : getSuffix(contract);
        return ContractId.generate(contract.getType(), contract.getCategory(), contract.getMemberId(), contractId);
    }

    private String getSuffix(Contract contract) {
        boolean isSongContract = isSongContract(contract.getCategory());
        if (isSongContract) {
            return StringUtils.EMPTY;
        }
        return utilService.getRandomAlphabeticString(5);
    }

    @Override
    public long limitAllowedByPlan(AccountType accountType, SubscriptionPlan plan) {
        LongSupplier sellerSupplier = () ->
                switch (plan) {
                    case FREE -> 2;
                    case PRO -> 4;
                    case UNLIMITED -> Long.MAX_VALUE;
                    default -> limitDefault();
                };

        return switch (accountType) {
            case LISTENER -> 0;
            case SELLER -> sellerSupplier.getAsLong();
        };
    }

    @Override
    public HashMap<String, Object> toTypeSenseDocument(Contract item) {
        HashMap<String, Object> map = new HashMap<>();
        // Required
        map.put("id", item.getId());
        map.put("position", item.getPosition() != null ? item.getPosition() : 0L);

        // Optional
        map.put("itemId", item.getId());
        map.put("memberId", item.getMemberId());
        map.put("status", item.getStatus());
        map.put("visibility", item.getVisibility());
        map.put("title", item.getTitle());
        map.put("defaultPrice", item.getDefaultPrice());
        map.put("minOfferPrice", item.getMinOfferPrice());
        map.put("price", item.getPrice());
        map.put("modified", item.getModified());

        Set<ContractDeliverable> deliverables = item.getDeliverables() != null ? item.getDeliverables() : Set.of();
        String[] deliverablesArray = deliverables.stream()
                .map(Enum::toString)
                .toArray(String[]::new);
        map.put("deliverables", deliverablesArray);

        map.put("type", item.getType());
        map.put("category", item.getCategory());
        map.put("weight", item.getWeight());
        map.put("time", item.getTime());
        map.put("description", item.getDescription());
        map.put("featureList", item.getFeatureList());
        map.put("downloaderRequirement", item.getDownloaderRequirement());

        Set<SocialMedia> socialMedias = item.getSocialMedias() != null ? item.getSocialMedias() : Set.of();
        String[] socialMediasArray = socialMedias.stream()
                .map(Enum::toString)
                .toArray(String[]::new);
        map.put("socialMedias", socialMediasArray);

        map.put("enabled", BooleanUtils.toBoolean(item.getEnabled()));
        map.put("offerOnly", BooleanUtils.toBoolean(item.getOfferOnly()));

        return map;
    }

    @Override
    public Optional<Contract> fromTypeSenseDocument(Map<String, Object> document) {
        if (!document.containsKey("itemId")) {
            return Optional.empty();
        }
        return Optional.ofNullable(get(String.valueOf(document.get("itemId")), null));
    }

    @Override
    public Client getTypeSenseClient() {
        return typeSenseClient;
    }

    @Override
    public String getTypeSenseCollectionName() {
        return "contract";
    }

    @Override
    public Class<Contract> getTypeSenseClass() {
        return Contract.class;
    }

    @Override
    public Set<String> getAvailableFacets() {
        return Set.of(
                "memberId",
                "status",
                "visibility",
                "deliverables",
                "type",
                "category",
                "weight",
                "downloaderRequirement",
                "socialMedias",
                "enabled",
                "offerOnly"
        );
    }

    // TODO - When the migration code is removed this code can be enhanced, removing the generate initial contracts call and in this method the search can be removed too
    @Override
    public Page<Contract> search(@Nullable Map<String, String> filters, Pageable pageable) {
        SearchFiltersBuilder searchFiltersBuilder = SearchFilters.builder()
                .shouldAppendStatus(false)
                .utilService(utilService)
                .archivedStatus(getArchivedStatus().name())
                .filters(filters)
                .requiredFilters(List.of("memberId", "type"));
        SearchFilters searchFilters = searchFiltersBuilder.build();

        String memberId = searchFilters.get("memberId");
        ContractType type = ContractType.valueOf(searchFilters.get("type"));

        if (searchFilters.onlyContainsRequiredFilters()) {
            return contractDao.getByPk(utilService.generateDynamoDbKey(memberId, type), pageable);
        }

        boolean hasFilterByStatusAndCategory = searchFilters.onlyContains("memberId", "type", "status", "category");
        boolean hasFilterByCategory = searchFilters.onlyContains("memberId", "type", "category");

        if (searchFilters.notContains("query", "deliverables") && (hasFilterByStatusAndCategory || hasFilterByCategory)) {
            return searchContractsInDao(pageable, searchFilters);
        }
        return ContractService.super.search(searchFilters.toMap(), pageable);
    }

    @Override
    public boolean shouldAppendStatus(Contract item) {
        return false;
    }

    private Page<Contract> searchContractsInDao(Pageable pageable, SearchFilters searchFilters) {
        String memberId = searchFilters.get("memberId");
        ContractType type = ContractType.valueOf(searchFilters.get("type"));
        ContractCategory category = getContractCategoryFromString(searchFilters.get("category"));
        ContractStatus status = getContractStatusFromString(searchFilters.get("status"));
        return list(memberId, type, category, status, pageable);
    }

    private ContractCategory getContractCategoryFromString(String categoryAsString) {
        ContractCategory category = null;
        if (StringUtils.isNotBlank(categoryAsString)) {
            category = ContractCategory.valueOf(categoryAsString);
        }
        return category;
    }

    private ContractStatus getContractStatusFromString(@Nullable String statusAsString) {
        ContractStatus status = null;
        if (StringUtils.isNotBlank(statusAsString)) {
            status = ContractStatus.valueOf(statusAsString);
        }
        return status;
    }

    private boolean isPageEmpty(Page<Contract> contractPage) {
        return contractPage.isEmpty();
    }

    private boolean isFirstPage(Page<Contract> contractPage) {
        return contractPage.getNumber() == 0;
    }

    private boolean isSongContract(@Nullable ContractCategory category) {
        return ContractCategory.SONG.equals(category);
    }

    private boolean isFreeContract(@Nullable ContractCategory category) {
        return ContractCategory.FREE.equals(category);
    }

    private boolean isRegularContract(@Nullable ContractCategory category) {
        return ContractCategory.REGULAR.equals(category);
    }

    private boolean isExclusiveContract(@Nullable ContractCategory category) {
        return ContractCategory.EXCLUSIVE.equals(category);
    }

    @Override
    @Async
    public void scan() {
        logger.debug("[SCAN] Starting scan ...");
        List<Contract> list = new ArrayList<>(100);

        Iterable<Contract> all = contractDao.findAll();
        Iterator<Contract> iterator = all.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            list.add(iterator.next());
            if (list.size() == 100) {
                count += list.size();
                logger.trace("[SCAN] pushing {} items", list.size());
                logger.debug("[SCAN] item count {}", count);
                applyScanChanges(list);
                list = new ArrayList<>(100);
            }
        }

        logger.trace("[SCAN] pushing {} items", list.size());
        applyScanChanges(list);
        logger.debug("[SCAN] Scan finished with " + count + " items processed.");
    }

    private void applyScanChanges(List<Contract> list) {
        upsertTypeSenseDocuments(list);
    }

    @Override
    public Page<Contract> getByPkAndSkStartingWith(String memberId,
                                                   ContractType type,
                                                   ContractCategory category,
                                                   ContractStatus status,
                                                   Pageable page) {
        return contractDao.getByPkAndSkStartingWith(utilService.generateDynamoDbKey(memberId, type),
                utilService.generateDynamoDbKey(category, status),
                page);
    }

    @Override
    public Page<Contract> findContractsPublishedByPk(String memberId, ContractType type, Pageable page) {
        return contractDao.findContractsPublishedByPk(utilService.generateDynamoDbKey(memberId, type), page);
    }

    @Override
    public void scanMember(String memberId) {
        TableScanner<Contract> scanner = TableScanner.of(1000, (pageable) -> getAllByMemberId(memberId, pageable));
        scanner.forByPage(this::upsertTypeSenseDocuments);
    }

    private Page<Contract> getAllByMemberId(String memberId, Pageable pageable) {
        return contractDao.findAllByMemberId(memberId, pageable);
    }

    @Override
    public void preset(String memberId, ContractType type, @Nullable ContractCategory category, ContractVersion version) {
        Pageable pageable = PageRequest.of(0, 100);
        Map<String, String> filters = buildDefaultFilters(memberId, type, null, category, null);
        Page<Contract> contractPage = search(filters, pageable);
        generateInitialContracts(contractPage, type, category, memberId, version);
    }

    @Override
    public String getTemplate(String contractId) {
        Contract contract = get(contractId, null);
        // [mauricio.ms] - Control needed until the template migration is run
        return Optional.ofNullable(contract.getTemplateId())
                .map(templateId -> contractTemplateService.get(templateId).getTemplate())
                .orElse(contract.getTemplate());
    }

    @Override
    public void updateTemplate(String contractId, String template) {
        logger.debug("[ContractServiceImpl:updateTemplate] (contractId={})", contractId);
        Contract contract = get(contractId, null);
        String previousTemplateId = contract.getTemplateId();
        ContractTemplate contractTemplate = contractTemplateService.createIfTemplateChanged(contract.getType(), contract.getCategory(), template);
        contract.setTemplateId(contractTemplate.getId());
        // TODO [mauricio.ms] - Remove default in model and remove this kind of checks (here and in save)
        if (contract.getSocialMedias() != null && contract.getSocialMedias().isEmpty()) {
            contract.setSocialMedias(null);
        }
        contractDao.save(contract);

        if (contractHistoryService.shouldCreateEntry(previousTemplateId, contractTemplate.getId())) {
            contractHistoryService.createEntry(contract.getId(), contractTemplate.getId());
        }
    }

    @Override
    public void refreshTemplate(String memberId) {
        TableScanner<Contract> scanner = TableScanner.of(1000, (pageable) -> getAllByMemberId(memberId, pageable));
        scanner.forEach(contract -> {
            setTemplateDefault(contract);
            setTemplateId(contract);
            save(contract);
        });
    }

    @Override
    public Page<Contract> listThatContainsDeliverables(String memberId,
                                                       ContractType type,
                                                       ContractCategory category,
                                                       Set<ContractDeliverable> deliverables,
                                                       Pageable pageable) {
        return contractDao.getAllThatContainsDeliverables(generatePk(memberId, type),
                generateSk(category, ContractStatus.PUBLISHED, null),
                deliverables,
                pageable);
    }

    private void generateInitialContracts(Page<Contract> currentContracts,
                                          ContractType type,
                                          @Nullable ContractCategory category,
                                          String memberId,
                                          ContractVersion version) {
        boolean isEmpty = isPageEmpty(currentContracts);
        boolean isFirstPage = isFirstPage(currentContracts);
        boolean shouldBeCreated = isEmpty && isFirstPage;
        if (!shouldBeCreated) {
            return;
        }

        boolean canHaveSongCategory = List.of(ContractType.TRACK, ContractType.ALBUM).contains(type);
        boolean canHaveExclusiveCategory = List.of(ContractType.TRACK, ContractType.ALBUM).contains(type);

        boolean hasCategory = nonNull(category);
        boolean isRegular = isRegularContract(category);
        boolean isFree = isFreeContract(category);
        boolean isSong = isSongContract(category);

        boolean shouldCreateRegularContract = !hasCategory || isRegular;
        boolean shouldCreateFreeContract = !hasCategory || isFree;
        boolean shouldCreateSongContract = (!hasCategory && canHaveSongCategory) || (isSong && canHaveSongCategory);
        boolean shouldCreateExclusiveContract = !hasCategory && canHaveExclusiveCategory;

        if (shouldCreateRegularContract) {
            createDefaultContract(type, ContractCategory.REGULAR, memberId, version);
        }
        if (shouldCreateFreeContract) {
            createDefaultContract(type, ContractCategory.FREE, memberId, version);
        }
        if (shouldCreateSongContract) {
            createDefaultContract(type, ContractCategory.SONG, memberId, version);
        }
        if (shouldCreateExclusiveContract) {
            createDefaultContract(type, ContractCategory.EXCLUSIVE, memberId, version);
        }
    }

    private String generatePk(Contract contract) {
        return generatePk(contract.getMemberId(), contract.getType());
    }

    private String generatePk(String memberId, ContractType type) {
        return utilService.generateDynamoDbKey(memberId, type);
    }

    private String generateSk(Contract contract) {
        return generateSk(contract.getCategory(), contract.getStatus(), contract.getPosition());
    }

    private String generateSk(@Nullable ContractCategory category, @Nullable ContractStatus status, @Nullable Long position) {
        List<Object> fragments = new ArrayList<>();
        if (nonNull(category)) {
            fragments.add(category);
        }
        if (nonNull(status)) {
            fragments.add(status);
        }
        if (nonNull(position)) {
            fragments.add(position);
        }
        return utilService.generateDynamoDbKey(fragments.toArray());
    }

    private Map<String, String> buildDefaultFilters(String memberId,
                                                    ContractType type,
                                                    @Nullable ContractStatus status,
                                                    @Nullable ContractCategory category,
                                                    @Nullable Set<ContractDeliverable> deliverables) {
        Map<String, String> filters = new HashMap<>();
        filters.put("memberId", memberId);
        filters.put("type", type.name());
        if (nonNull(status)) {
            filters.put("status", ContractStatus.PUBLISHED.name());
        }
        if (nonNull(category)) {
            filters.put("category", category.name());
        }
        if (CollectionUtils.isNotEmpty(deliverables)) {
            Set<ContractDeliverable> deliverablesFilter
                    = Arrays.stream(ContractDeliverable.values())
                    .filter(item -> !deliverables.contains(item))
                    .collect(Collectors.toSet());
            if (!deliverablesFilter.isEmpty()) {
                String deliverablesToFilter = deliverablesFilter.stream().map(ContractDeliverable::name).collect(Collectors.joining(","));
                filters.put("exclude", String.format("deliverables#%s", String.join(",", deliverablesToFilter)));
            }
        }
        return filters;
    }

    private List<Contract> listByAttachment(Page<ContractAttachment> contractAttachmentPage) {
        List<Contract> contracts = new ArrayList<>();

        for (ContractAttachment contractAttachment : contractAttachmentPage.getContent()) {
            Optional<Contract> optionalContract = get(contractAttachment.getContractId());
            if (optionalContract.isEmpty()) {
                continue;
            }
            Contract contract = optionalContract.get();
            if (isPublished(contract)) {
                contract.setDefaultPrice(contract.getPrice());
                contract.setEnabled(contractAttachment.getIsEnabled());
                contract.setPrice(contractAttachment.getPrice());

                boolean hasOfferOnly = nonNull(contractAttachment.getOfferOnly());
                Boolean isPriceEqualZero = contractAttachment.getPrice() == 0 ? Boolean.TRUE : Boolean.FALSE;
                boolean isOfferOnly = hasOfferOnly ? contractAttachment.getOfferOnly() : isPriceEqualZero;
                contract.setOfferOnly(isOfferOnly);

                contracts.add(contract);
            }
        }

        return contracts;
    }

    private List<Contract> listAttachedContracts(String itemId, @Nullable ContractCategory category) {
        List<Contract> contracts = new ArrayList<>();
        Page<ContractAttachment> page;
        Pageable pageable = PageRequest.of(0, 100);

        do {
            page = contractAttachmentService.listByItem(itemId, false, pageable);
            contracts.addAll(listByAttachment(page));
            pageable = page.nextPageable();
        } while (page.hasNext());

        contracts = filterByPublished(contracts);
        contracts = filterByCategory(contracts, itemId, category);

        return contracts;
    }

    private List<Contract> filterByPublished(List<Contract> contracts) {
        return contracts
                .stream()
                .filter(this::isPublished)
                .collect(Collectors.toList());
    }

    private List<Contract> filterByCategory(List<Contract> contracts,
                                            String itemId,
                                            @Nullable ContractCategory category) {
        if (isNull(category)) {
            return contracts;
        }

        return contracts
                .stream()
                .filter(c -> c.getCategory().equals(category))
                .filter(c -> isValidContractForType(c, getContractType(itemId), category))
                .collect(Collectors.toList());
    }

    private Page<Contract> getDefaultContractsForAttachment(String itemId,
                                                            String memberId,
                                                            @Nullable ContractCategory category,
                                                            @Nullable Set<ContractDeliverable> deliverables,
                                                            Pageable pageable) {
        Map<String, String> filters = buildDefaultFilters(memberId, getContractType(itemId), ContractStatus.PUBLISHED, category, deliverables);
        Page<Contract> contractPage = search(filters, pageable);
        contractPage.map(contract -> {
            Optional<ContractAttachment> optionalContractAttachment = contractAttachmentService.getOptional(contract.getId(), itemId);
            if (optionalContractAttachment.isPresent()) {
                contract.setDefaultPrice(contract.getPrice());
                contract.setEnabled(optionalContractAttachment.get().getIsEnabled());
                contract.setPrice(optionalContractAttachment.get().getPrice());
            }
            return contract;
        });

        return contractPage;
    }

    private Page<Contract> search(String itemId,
                                  String memberId,
                                  @Nullable ContractCategory category,
                                  @Nullable Set<ContractDeliverable> deliverables,
                                  Pageable pageable) {
        Map<String, String> filters = buildDefaultFilters(memberId, getContractType(itemId), ContractStatus.PUBLISHED, category, deliverables);
        return search(filters, pageable);
    }

    protected long getUserId(String memberId) {
        return Long.parseLong(memberId.replace("MR", ""));
    }

    private boolean isV2IdRequired(Contract contract) {
        boolean isSongContract = isSongContract(contract.getCategory());
        boolean isFreeContract = isFreeContract(contract.getCategory());
        boolean hasLegacySupport = ContractType.TRACK.equals(contract.getType()) || ContractType.ALBUM.equals(contract.getType());
        return isNull(contract.getV2Id()) && hasLegacySupport && !isSongContract && !isFreeContract;
    }

    private void setTemplateDefault(Contract contract) {
        String template = getResourceFileAsString(
                String.format("templates/%s/%s.md",
                        contract.getType().name().toLowerCase(),
                        contract.getCategory().name().toLowerCase())
        );
        contract.setTemplate(template);
    }

    private String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    private InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ContractServiceImpl.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    private boolean isValidContractForType(Contract contract, ContractType type, @Nullable ContractCategory category) {
        switch (type) {
            case TRACK, ALBUM:
                if (isSongContract(category)) {
                    return isFreeContract(contract.getCategory()) || isSongContract(contract.getCategory());
                } else {
                    return isRegularContract(contract.getCategory()) ||
                            isExclusiveContract(contract.getCategory()) ||
                            isFreeContract(contract.getCategory());
                }
            case SOUNDKIT:
                return isRegularContract(contract.getCategory()) || isFreeContract(contract.getCategory());
            default:
                return false;
        }
    }

    private ContractType getContractType(String itemId) {
        return Objects.requireNonNull(ContractType.of(itemId));
    }

    private void notifyLegacy(Contract contract) {
        if (shouldNotifyLegacy(contract)) {
            legacyAsyncClient.sendMessage(contract, contract.getId());
        }
    }

    private void notifyLegacy(Contract contract, Map<String, Object> additionalHeaders) {
        if (shouldNotifyLegacy(contract)) {
            ContractDto payload = modelMapper.map(contract, ContractDto.class);

            Map<String, Object> headers = legacyAsyncClient.getDefaultHeaders(contract.getId());
            headers.putAll(additionalHeaders);

            legacyAsyncClient.sendMessage(payload, contract.getId(), headers);
        }
    }

    private boolean shouldNotifyLegacy(Contract contract) {
        return Boolean.FALSE.equals(contract.getDisableLegacyNotification());
    }

    @Override
    public ContractStatus getDeletableStatus() {
        return ContractStatus.DRAFT;
    }

    @Override
    public Contract resetTemplate(String contractId, String memberId) {
        Contract contract = get(contractId, memberId);
        ResetContractTemplate resetContractTemplate = contractHistoryService.getInitialTemplate(contractId);
        contract.setTemplate(resetContractTemplate.getTemplate());
        contract.setTemplateId(resetContractTemplate.getContractTemplateId());
        return save(contract);
    }


}
package samples;

import com.beatstars.common.App;
import com.beatstars.contract.service.ContractAttachmentService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class DeleteItemEvent extends App {

    private final ContractAttachmentService contractAttachmentService;

    @SqsListener(value = "${app.contract.delete.item.sqs}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
    public void execute(PayloadDto payloadDto) {
        logger.trace("[DeleteItemEvent:execute] (payloadDto={})", payloadDto);
        contractAttachmentService.deleteAttachmentsForItem(payloadDto.getItemId());
    }

    @Builder
    @Getter
    @ToString
    public static class PayloadDto {

        @NotNull
        private String itemId;
    }
}
package cloudformationparser;

import org.antlr.v4.runtime.RuleContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CloudFormationTemplateToSymbolsTable extends YamlBaseVisitor {

    private Map<String, Map<String, Object>> parametersTable;

    private final Map<String, AbstractResource> sqsQueuesTable;

    private final Map<String, AbstractResource> snsTopicsTable;

    private final Map<String, SsmParameter> ssmParametersTable;

    public CloudFormationTemplateToSymbolsTable() {
        sqsQueuesTable = new HashMap<>();
        snsTopicsTable = new HashMap<>();
        ssmParametersTable = new HashMap<>();
    }

    enum ResourceType {
        SQS_QUEUE("AWS::SQS::Queue"),
        SNS_TOPIC("AWS::SNS::Topic"),
        SSM_PARAMETER("AWS::SSM::Parameter");

        private final String value;

        ResourceType(String value) {
            this.value = value;
        }

        public static ResourceType get(String value) {
            return Stream.of(values())
                    .filter(v -> v.value.equals(value))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static class AbstractResource {
        private final Map<String, Object> parameters;

        AbstractResource(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        public Map<String, Object> parameters() {
            return parameters;
        }

        @Override
        public String toString() {
            return parameters.toString();
        }
    }

    static class SsmParameter {
        private final String name;
        private final String resourceKey;
        private final String resourceAttributeValue;

        SsmParameter(String name, String resourceKey, String resourceAttributeValue) {
            this.name = name;
            this.resourceKey = resourceKey;
            this.resourceAttributeValue = resourceAttributeValue;
        }

        @Override
        public String toString() {
            return name + " -> " + resourceKey + "." + resourceAttributeValue;
        }
    }

    @Override
    public Object visitObject(YamlParser.ObjectContext ctx) {
        switch (ctx.key().getText()) {
            case "Parameters" -> {
                parametersTable = parseObject(ctx);
                Map<String, Object> env = parametersTable.get("Env");
                if (env != null && !env.containsKey("Default")) {
                    env.put("Default", "dev");
                }
            }
            case "Resources" -> parseResources(ctx);
        }

        return new Object();
    }

    private void parseResources(YamlParser.ObjectContext ctx) {
        for (var resource : ctx.objectbody().statement()) {
            var resourceObject = resource.object();
            ResourceType resourceType = getResourceType(resourceObject);
            if (resourceType == null) {
                continue;
            }

            String resourceKey = resourceObject.key().getText();
            switch (resourceType) {
                case SQS_QUEUE -> sqsQueuesTable.put(resourceKey, new AbstractResource(parseParameters(resourceObject)));
                case SNS_TOPIC -> snsTopicsTable.put(resourceKey, new AbstractResource(parseParameters(resourceObject)));
                case SSM_PARAMETER -> {
//                    var properties = parseParameters(resourceObject);
//                    Map.Entry<String, String> value = (Map.Entry<String, String>) properties.get("Value");
//                    ssmParametersTable.put(resourceKey, new SsmParameter((String) properties.get("Name"), value.getKey(), value.getValue()));
                }
            }
        }
    }

    private ResourceType getResourceType(YamlParser.ObjectContext ctx) {
        if (ctx == null) {
            return null;
        }

        for (var statement : ctx.objectbody().statement()) {
            var mapping = statement.mapping();
            if (mapping != null) {
                var mappingEntry = parseMapping(mapping);
                if ("Type".equals(mappingEntry.getKey())) {
                    return ResourceType.get(mappingEntry.getValue().toString());
                }
            }
        }

        return null;
    }

    private Map<String, Object> parseParameters(YamlParser.ObjectContext ctx) {
        Map<String, Object> parameters = new HashMap<>();

        for (var property : ctx.objectbody().statement()) {
            var propertyObject = property.object();
            if (propertyObject != null && "Properties".equals(propertyObject.key().getText())) {
                for (var propertyStatement : propertyObject.objectbody().statement()) {
                    if (propertyStatement.tagList() != null) {
                        parameters.put(propertyStatement.tagList().key().getText(), parseTagList(propertyStatement.tagList()));
                    } else if (propertyStatement.mapping() != null) {
                        var mappingEntry = parseMapping(propertyStatement.mapping());
                        if (mappingEntry != null) {
                            parameters.put(mappingEntry.getKey(), mappingEntry.getValue());
                        }
                    } else if (propertyStatement.object() != null) {
                        if ("Parameters".equals(propertyStatement.object().key().getText())) {
                            parameters.putAll(parseObject(propertyStatement.object()));
                        }
                    }
                }
            }
        }

        return parameters;
    }

    private String parseTagList(YamlParser.TagListContext ctx) {
        String tag = ctx.NAME().getText();
        if ("Join".equals(tag)) {
            var list = ctx.list();
            String separator = (String) parseValue(list.listitem(0).listitemvalue().value());
            var arguments = list.listitem(1);
            return parseListItem(separator, arguments);
        }
        return null;
    }

    private String parseListItem(String separator, YamlParser.ListitemContext ctx) {
        StringBuilder parsed = new StringBuilder();

        var itemValue = ctx.listitemvalue();
        var itemValueList = itemValue.list();
        if (itemValueList != null) {
            var items = itemValueList.listitem();
            if (items.size() == 1) {
                parsed.append(parseListItem(separator, items.get(0)));
            } else {
                parsed.append(parseListItem(separator, items.get(0)));
                for (int i = 1; i < items.size(); i++) {
                    parsed.append(separator);
                    var item = items.get(i);
                    parsed.append(parseListItem(separator, item));
                }
            }
        } else if (itemValue.value() != null) {
            parsed.append(parseValue(itemValue.value()));
        }

        var itemsRest = ctx.listitemvaluerest();
        if (itemsRest != null) {
            var itemsRestValues = itemsRest.listitemvalue();
            if (!itemsRestValues.isEmpty()) {
                for (var itemRest : itemsRestValues) {
                    if (itemRest.list() != null) {
                        for (var item : itemRest.list().listitem()) {
                            parsed.append(separator).append(parseListItem(separator, item));
                        }
                    } else {
                        parsed.append(separator).append(parseListItemValue(itemRest));
                    }
                }
            }
        }

        return parsed.toString();
    }

    private String parseListItemValue(YamlParser.ListitemvalueContext ctx) {
        if (ctx.value() != null) {
            return (String) parseValue(ctx.value());
        }
        return null;
    }

    private Map.Entry<String, Object> parseMapping(YamlParser.MappingContext ctx) {
        Object value = parseValue(ctx.value());
        if (value == null) {
            return null;
        }
        return Map.entry(ctx.key().getText(), value);
    }

    private Map<String, Map<String, Object>> parseObject(YamlParser.ObjectContext ctx) {
        Map<String, Map<String, Object>> object = new HashMap<>();
        for (var statement : ctx.objectbody().statement()) {
            var statementMapping = statement.mapping();
            if (statementMapping != null) {
                Map<String, Object> mappingEntry = new HashMap<>();
                mappingEntry.put("Value", parseValue(statementMapping.value()));
                object.put(statementMapping.key().getText(), mappingEntry);
            } else {
                var statementObject = statement.object();
                if (statementObject != null) {
                    String key = statementObject.key().getText();
                    object.put(key, new HashMap<>());
                    for (var property : statementObject.objectbody().statement()) {
                        Map.Entry<String, Object> propertyEntry = parseStatement(property);
                        if (propertyEntry != null) {
                            object.get(key).put(propertyEntry.getKey(), propertyEntry.getValue());
                        }
                    }
                }
            }
        }

        return object;
    }

    private Object parseValue(YamlParser.ValueContext ctx) {
        var tagArray = ctx.tagArray();
        if (tagArray != null) {
            return parseTagArray(tagArray);
        }
        var array = ctx.array();
        if (array != null) {
            return parseArray(array);
        }
        var parameter = ctx.parameter();
        if (parameter != null) {
            return parseParameter(parameter);
        }
        var attributeGetter = ctx.attributeGetter();
        if (attributeGetter != null) {
            return parseAttributeGetter(attributeGetter);
        }

        return ctx.getText()
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }

    private String parseTagArray(YamlParser.TagArrayContext ctx) {
        String tag = ctx.NAME().getText();
        if ("Join".equals(tag)) {
            var array = ctx.array();
            return parseArray((String) parseValue(array.value(0)), array.value(1).array());
        }
        return null;
    }

    // TODO can be avoided
    private String parseArray(YamlParser.ArrayContext ctx) {
        return parseArray("", ctx);
    }

    private String parseArray(String separator, YamlParser.ArrayContext ctx) {
        var values = ctx.value();
        if (values.isEmpty()) {
            return "";
        } else if (values.size() == 1) {
            return (String) parseValue(values.get(0));
        }

        StringBuilder parsed = new StringBuilder();
        parsed.append(parseValue(values.get(0)));
        for (int i = 1; i < values.size(); i++) {
            parsed.append(separator);
            var value = values.get(i);
            var array = value.array();
            if (array != null) {
                parsed.append(parseArray(separator, array));
            } else {
                parsed.append(parseValue(value));
            }
        }
        return parsed.toString();
    }

    private String parseParameter(YamlParser.ParameterContext ctx) {
        String name = ctx.NAME().getText();
        if (!parametersTable.containsKey(name)) {
            return null;
        }
        Map<String, Object> parameter = parametersTable.get(name);
        return (String) parameter.getOrDefault("Value", parameter.get("Default"));
    }

    private Map.Entry<String, String> parseAttributeGetter(YamlParser.AttributeGetterContext ctx) {
        return Map.entry(ctx.attribute().NAME(0).getText(), ctx.attribute().NAME(1).getText());
    }

    private Map.Entry<String, Object> parseStatement(YamlParser.StatementContext ctx) {
        var propertyMapping = ctx.mapping();
        if (propertyMapping != null) {
            return Map.entry(propertyMapping.key().getText(), parseValue(propertyMapping.value()));
        } else {
            var propertyObject = ctx.object();
            if (propertyObject != null) {
                var arguments = propertyObject.list();
                if (arguments != null) {
                    return Map.entry(propertyObject.key().getText(), arguments.listitem().stream().map(RuleContext::getText).toList());
                }
            }
        }
        return null;
    }

    public Map<String, AbstractResource> getSqsQueuesTable() {
        return sqsQueuesTable;
    }

    public Map<String, AbstractResource> getSnsTopicsTable() {
        return snsTopicsTable;
    }

    public Map<String, SsmParameter> getSsmParametersTable() {
        return ssmParametersTable;
    }

    @Override
    public String toString() {
        return "==== Parameters ====\n" + mapToString(parametersTable) + "\n\n" +
                "==== SQS Queues ====\n" + mapToString(sqsQueuesTable) + "\n\n" +
                "==== SNS Topics ====\n" + mapToString(snsTopicsTable) + "\n\n" +
                "==== SSM Parameters ====\n" + mapToString(ssmParametersTable);
    }

    private String mapToString(Map<?, ?> map) {
        if (map.isEmpty()) {
            return "";
        } else {
            StringBuilder mapAsString = new StringBuilder();
            Object[] keys = map.keySet().toArray();
            mapAsString
                    .append(keys[0].toString())
                    .append("\n\t")
                    .append(map.get(keys[0]));
            for (int i = 1; i < map.size(); i++) {
                mapAsString
                        .append("\n")
                        .append(keys[i].toString())
                        .append("\n\t")
                        .append(map.get(keys[i]));
            }
            return mapAsString.toString();
        }
    }
}
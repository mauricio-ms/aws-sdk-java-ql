package cloudformationparser;

import org.antlr.v4.runtime.RuleContext;

import java.util.HashMap;
import java.util.Map;

public class CloudFormationTreeGenerator extends YamlBaseVisitor {

    private Map<String, Map<String, Object>> parametersTable;

    private final Map<String, Map<String, Object>> resourcesTable;

    public CloudFormationTreeGenerator() {
        resourcesTable = new HashMap<>();
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
            case "Resources" -> {
                System.out.println("Resources");
                for (var resource : ctx.objectbody().statement()) {
                    var resourceObject = resource.object();
                    String resourceKey = resourceObject.key().getText();
                    resourcesTable.put(resourceKey, new HashMap<>());
                    for (var property : resourceObject.objectbody().statement()) {
                        var propertyObject = property.object();
                        if (propertyObject != null && "Properties".equals(propertyObject.key().getText())) {
                            for (var propertyStatement : propertyObject.objectbody().statement()) {
                                if (propertyStatement.mapping() != null) {
                                    var mappingEntry = parseMapping(propertyStatement.mapping());
                                    if (mappingEntry != null) {
                                        resourcesTable.get(resourceKey).put(mappingEntry.getKey(), mappingEntry.getValue());
                                    }
                                } else if (propertyStatement.object() != null) {
                                    if ("Parameters".equals(propertyStatement.object().key().getText())) {
                                        resourcesTable.get(resourceKey).putAll(parseObject(propertyStatement.object()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return new Object();
    }

    private Map.Entry<String, String> parseMapping(YamlParser.MappingContext ctx) {
        String value = parseValue(ctx.value());
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

    private String parseValue(YamlParser.ValueContext ctx) {
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

        return ctx.getText()
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }

    private String parseTagArray(YamlParser.TagArrayContext ctx) {
        String tag = ctx.NAME().getText();
        if ("Join".equals(tag)) {
            var array = ctx.array();
            return parseArray(parseValue(array.value(0)), array.value(1).array());
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
            return parseValue(values.get(0));
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

    @Override
    public String toString() {
        return "Parameters:\n" + parametersTable + "\nResources:\n" + resourcesTable + "\n";
    }
}
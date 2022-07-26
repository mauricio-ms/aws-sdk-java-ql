package cloudformationparser;

import org.antlr.v4.runtime.RuleContext;

import java.util.HashMap;
import java.util.Map;

public class CloudFormationTreeGenerator extends YamlBaseVisitor {

    private final Map<String, Map<String, Object>> parametersTable;

    public CloudFormationTreeGenerator() {
        parametersTable = new HashMap<>();
    }
    @Override
    public Object visitObject(YamlParser.ObjectContext ctx) {
        switch (ctx.key().getText()) {
            case "Parameters" -> parseParameters(ctx);
            case "Resources" -> {
                System.out.println("Resources");
                for (var resource : ctx.objectbody().statement()) {
                    var resourceObject = resource.object();
                    System.out.println(resourceObject.key().getText());
                    for (var property : resourceObject.objectbody().statement()) {
                        if (property.object() != null && "Properties".equals(property.object().key().getText())) {
                            System.out.println("Properties");
                            System.out.println(property.object().objectbody().statement(0).mapping().value().getText());
                        }
                        System.out.println("-> " + property.getText());
                    }
                }
                return super.visitObject(ctx.objectbody().statement(0).object().objectbody().statement(8).object());
            }
        }

        return super.visitObject(ctx);
    }

    private void parseParameters(YamlParser.ObjectContext ctx) {
        for (var statement : ctx.objectbody().statement()) {
            var object = statement.object();
            String key = object.key().getText();
            parametersTable.put(key, new HashMap<>());
            for (var property : object.objectbody().statement()) {
                Map.Entry<String, Object> propertyEntry = parseStatement(property);
                if (propertyEntry != null) {
                    parametersTable.get(key).put(propertyEntry.getKey(), propertyEntry.getValue());
                }
            }
        }
    }

    private Map.Entry<String, Object> parseStatement(YamlParser.StatementContext ctx) {
        var propertyMapping = ctx.mapping();
        if (propertyMapping != null) {
            return Map.entry(propertyMapping.key().getText(), propertyMapping.value().getText());
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

    private void parseResources(YamlParser.ObjectContext ctx) {
        System.out.println("Parse Resources");
    }

    @Override
    public String toString() {
        return parametersTable.toString();
    }
}
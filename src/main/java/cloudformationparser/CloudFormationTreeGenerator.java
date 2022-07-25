package cloudformationparser;

import graph.Node;
import org.antlr.v4.runtime.RuleContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudFormationTreeGenerator extends YamlBaseListener {

    private final Node nodeProject;

    private final Map<String, Map<String, Object>> parametersTable;

    public CloudFormationTreeGenerator(Node nodeProject) {
        this.nodeProject = nodeProject;
        parametersTable = new HashMap<>();
    }

    @Override
    public void enterObject(YamlParser.ObjectContext ctx) {
        if ("Parameters".equals(ctx.key().getText())) {
            for (var statement : ctx.objectbody().statement()) {
                var object = statement.object();
                String key = object.key().getText();
                parametersTable.put(key, new HashMap<>());
                for (var property : object.objectbody().statement()) {
                    var propertyMapping = property.mapping();
                    if (propertyMapping != null) {
                        parametersTable.get(key).put(propertyMapping.key().getText(), propertyMapping.value().getText());
                    } else {
                        var propertyObject = property.object();
                        if (propertyObject != null) {
                            var arguments = propertyObject.list();
                            if (arguments != null) {
                                List<String> values = arguments.listitem().stream().map(RuleContext::getText).toList();
                                parametersTable.get(key).put(propertyObject.key().getText(), values);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return parametersTable.toString();
    }
}
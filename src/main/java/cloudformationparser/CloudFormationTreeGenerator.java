package cloudformationparser;

import graph.Node;

import java.util.HashMap;
import java.util.Map;

public class CloudFormationTreeGenerator extends YamlBaseListener {

    private final Node nodeProject;

    private final Map<String, Map<String, String>> parametersTable;

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
                    var mapping = property.mapping();
                    if (mapping == null) {
                        continue;
                    }
                    parametersTable.get(key).put(mapping.key().getText(), mapping.value().getText());
                }
            }
        }
    }

    @Override
    public String toString() {
        return parametersTable.toString();
    }
}
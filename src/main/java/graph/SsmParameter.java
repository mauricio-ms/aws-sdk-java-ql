package graph;

import cloudformationparser.CloudFormationSymbolsTable;

public class SsmParameter {

    private final Node root;

    private final Node projectNode;

    private final String name;

    private String defaultValue;

    public SsmParameter(Node projectNode, String value) {
        root = projectNode.root();
        this.projectNode = projectNode;
        String literalValue = value.startsWith("${") && value.endsWith("}") ?
                value.substring(2, value.length() - 1) :
                value;
        String[] nameParts = literalValue.split(":");
        if (nameParts.length > 1) {
            name = nameParts[0];
            defaultValue = nameParts[1];
        } else {
            name = literalValue;
        }
    }

    public String resolve() {
        return resolve(projectNode);
    }

    private String resolve(Node currentNode) {
        for (Node cloudFormationSymbolsTableNode : currentNode.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            CloudFormationSymbolsTable.SsmParameter ssmParameter = cloudFormationSymbolsTable.getSsmParameter(name);
            if (ssmParameter != null) {
                return (String) cloudFormationSymbolsTable.findSsmParameterValue(ssmParameter);
            }

            for (CloudFormationSymbolsTable.CloudFormationStack stack : cloudFormationSymbolsTable.getStacks()) {
                // Node node findNode for stack.service
                Node stackNode = root.find("api-" + stack.getService(), Node.Type.API, Node.Type.LIB);
                if (stackNode == null) {
                    continue;
                }
                String stackParameter = resolve(stackNode);
                // if resolveParameter != null for node and parameter, so returns it
                if (stackParameter != null) {
                    return stackParameter;
                }
            }
        }

        return defaultValue;
    }
}

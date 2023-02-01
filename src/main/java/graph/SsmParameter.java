package graph;

import cloudformationparser.CloudFormationSymbolsTable;

public class SsmParameter {

    private final Node root;

    private final Node projectNode;

    private final String name;

    public SsmParameter(Node projectNode, String name) {
        root = projectNode.root();
        this.projectNode = projectNode;
        if (name.startsWith("${") && name.endsWith("}")) {
            this.name = name.substring(2, name.length() - 1);
        } else {
            this.name = name;
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
                Node stackNode = root.find("api-" + stack.getService(), Node.Type.PROJECT, Node.Type.LIB);
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

        return null;
    }
}

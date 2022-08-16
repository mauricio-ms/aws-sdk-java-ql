package graph;

import cloudformationparser.CloudFormationSymbolsTable;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.util.function.Consumer;

public class TreeListener implements Consumer<Node> {

    private Node root;

    private Node currentProjectNode;

    private Node currentClassNode;

    public TreeListener(Node root) {
        this.root = root;
    }

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case PROJECT -> currentProjectNode = node;
            case CLASS -> currentClassNode = node;
            case SQS_LISTENER -> {
//                ServicesCommunicationGraph.add(resolveResource(node.value.toString()), currentProjectNode.value.toString());
                System.out.println("---->> " + node.value.toString());
                String sourceService = resolveResource(node.value.toString());
                if (sourceService != null) {
                    ServicesGraph.addEdge(ServicesSymbolTable.getKey(sourceService), ServicesSymbolTable.getKey(currentProjectNode.value.toString()));
                } else {
                    System.out.println("Source Service not added to the tree: " + node.value);
                }
            }
            case SNS_SENDER -> {
                Node child = node.children.get(0);
                switch (child.type) {
                    case METHOD_CALL -> {
                        MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) child.value;
                        Node propertiesVariableNode = currentProjectNode.find(methodCallNodeValue.caller).parent;
                        Node propertiesNode = currentProjectNode.find(propertiesVariableNode.value, Node.Type.INTERFACE);
                        Node propertiesImplNode = propertiesNode.findUnique(Node.Type.CLASS);
                        String snsTopic = (String) propertiesImplNode.find(methodCallNodeValue.call).children.get(0).value;
//                        ServicesCommunicationGraph.add(currentProjectNode.value.toString(), resolveResource(snsTopic));
                        ServicesGraph.addEdge(ServicesSymbolTable.getKey(currentProjectNode.value.toString()), ServicesSymbolTable.getKey(resolveResource(snsTopic)));
                    }
                    case INSTANCE_VARIABLE_ID -> {
                        String variableId = (String) child.value;
                        Node variableDeclarationNode = currentProjectNode.find(variableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                        String snsTopic = (String) variableDeclarationNode.children.get(0).value;
//                        ServicesCommunicationGraph.add(currentProjectNode.value.toString(), resolveResource(snsTopic));
                        ServicesGraph.addEdge(ServicesSymbolTable.getKey(currentProjectNode.value.toString()), ServicesSymbolTable.getKey(resolveResource(snsTopic)));
                    }
                }
            }
        }
    }

    private String resolveResource(String resource) {
        boolean isParameter = resource.startsWith("${") && resource.endsWith("}");
        if (isParameter) {
            try {
                return resolveParameter(currentProjectNode, resource.substring(2, resource.length()-1));
//                return GetAwsParameter.get("/config/application_dev/" + resource.substring(2, resource.length()-1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return resource;
    }

    private String resolveParameter(Node projectNode, String parameter) {
        for (Node cloudFormationSymbolsTableNode : projectNode.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            CloudFormationSymbolsTable.SsmParameter ssmParameter = cloudFormationSymbolsTable.getSsmParameter(parameter);
            if (ssmParameter != null) {
                return (String) cloudFormationSymbolsTable.findSsmParameterValue(ssmParameter);
            }

            for (CloudFormationSymbolsTable.CloudFormationStack stack : cloudFormationSymbolsTable.getStacks()) {
                // Node node findNode for stack.service
                Node stackNode = root.find("api-" + stack.getService(), Node.Type.PROJECT);
                if (stackNode == null) {
                    continue;
                }
                String stackParameter = resolveParameter(stackNode, parameter);
                // if resolveParameter != null for node and parameter, so returns it
                if (stackParameter != null) {
                    return stackParameter;
                }
            }
        }

        return null;
    }
}
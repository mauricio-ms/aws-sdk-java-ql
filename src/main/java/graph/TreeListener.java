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
                System.out.println("---->> " + node.value.toString());
                String sourceService = resolveResource(node.value.toString());
                if (sourceService != null) {
                    System.out.printf("SQS_LISTENER: %s -> %s\n", sourceService, currentProjectNode.value);
                    ServicesGraph.addEdge(ServicesSymbolTable.getId(sourceService), ServicesSymbolTable.getId(currentProjectNode.value.toString()));
                } else {
                    System.out.println("Parameter not resolved: " + node.value);
                    Integer keyNotResolvedParameter = ServicesSymbolTable.add(node.value.toString(), ServicesSymbolTable.Resource::nonResolved);
                    ServicesGraph.addEdge(keyNotResolvedParameter, ServicesSymbolTable.getId(currentProjectNode.value.toString()));
                }
            }
            case SQS_SENDER, SNS_SENDER -> {
                Node child = node.children.get(0);
                switch (child.type) {
                    case METHOD_CALL -> {
                        MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) child.value;
                        Node propertiesVariableNode = currentProjectNode.find(methodCallNodeValue.caller).parent;
                        Node propertiesNode = currentProjectNode.find(propertiesVariableNode.value, Node.Type.INTERFACE);
                        Node propertiesImplNode = propertiesNode.findUnique(Node.Type.CLASS);
                        String destination = (String) propertiesImplNode.find(methodCallNodeValue.call).children.get(0).value;
                        String destinationResource = resolveResource(destination);
                        if (destinationResource != null) {
                            System.out.printf("MESSAGING_SENDER::METHOD_CALL: %s -> %s\n", currentProjectNode.value, destinationResource);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), ServicesSymbolTable.getId(destinationResource));
                        } else {
                            System.out.println("Parameter not resolved: " + destination);
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), keyNotResolvedParameter);
                        }
                    }
                    case INSTANCE_VARIABLE_ID -> {
                        String variableId = (String) child.value;
                        Node variableDeclarationNode = currentProjectNode.find(variableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                        String destination = (String) variableDeclarationNode.children.get(0).value;
                        String destinationResource = resolveResource(destination);
                        if (destinationResource != null) {
                            System.out.printf("MESSAGING_SENDER::INSTANCE_VARIABLE_ID: %s -> (%s:%s)\n", currentProjectNode.value, destination, destinationResource);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), ServicesSymbolTable.getId(destinationResource));
                        } else {
                            System.out.println("Parameter not resolved: " + destination);
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), keyNotResolvedParameter);
                        }
                    }
                }
            }
        }
    }

    private String resolveResource(String resource) {
        boolean isParameter = resource.startsWith("${") && resource.endsWith("}");
        if (isParameter) {
            try {
                return resolveParameter(root, resource.substring(2, resource.length() - 1));
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
                Node stackNode = root.find("api-" + stack.getService());
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
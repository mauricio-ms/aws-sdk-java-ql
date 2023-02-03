package graph;

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
                    ServicesGraph.addEdge(ServicesSymbolTable.getId(sourceService), ServicesSymbolTable.getId(currentProjectNode.value.toString()), 1);
                } else {
                    System.out.println("Parameter not resolved: " + node.value);
                    Integer keyNotResolvedParameter = ServicesSymbolTable.add(node.value.toString(), ServicesSymbolTable.Resource::nonResolved);
                    ServicesGraph.addEdge(keyNotResolvedParameter, ServicesSymbolTable.getId(currentProjectNode.value.toString()), 1);
                }
            }
            case SQS_SENDER, SNS_SENDER -> {
                Node child = node.children.get(0);
                switch (child.type) {
                    case METHOD_CALL -> {
                        MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) child.value;
                        String destination = new MethodEvaluator(root, (String) currentClassNode.value, methodCallNodeValue).evaluate();
                        String destinationResource = resolveResource(destination);
                        if (destinationResource != null) {
                            System.out.printf("MESSAGING_SENDER::METHOD_CALL: %s -> %s\n", currentProjectNode.value, destinationResource);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), ServicesSymbolTable.getId(destinationResource), 1);
                        } else {
                            System.out.println("Parameter not resolved: " + destination);
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), keyNotResolvedParameter, 1);
                        }
                    }
                    case INSTANCE_VARIABLE_ID -> {
                        String variableId = (String) child.value;
                        Node variableDeclarationNode = currentProjectNode.find(variableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                        String destination = (String) variableDeclarationNode.children.get(0).value;
                        String destinationResource = resolveResource(destination);
                        if (destinationResource != null) {
                            System.out.printf("MESSAGING_SENDER::INSTANCE_VARIABLE_ID: %s -> (%s:%s)\n", currentProjectNode.value, destination, destinationResource);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), ServicesSymbolTable.getId(destinationResource), 1);
                        } else {
                            System.out.println("Parameter not resolved: " + destination);
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(ServicesSymbolTable.getId(currentProjectNode.value.toString()), keyNotResolvedParameter, 1);
                        }
                    }
                }
            }
        }
    }

    private String resolveResource(String resource) {
        SsmParameter ssmParameter = new SsmParameter(currentProjectNode, resource);
        return ssmParameter.resolve();
    }
}
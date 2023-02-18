package tree;

import graph.MethodCallNodeValue;
import graph.MethodEvaluator;
import graph.Node;
import services.ServicesGraph;
import services.ServicesSymbolTable;

public class TreeListener implements TreeListenable {

    private final Node root;

    private final ResourceResolverStrategy resourceResolverStrategy;

    private Integer sourceServiceId;

    private Node currentProjectNode;

    private Node currentClassNode;

    public TreeListener(Node root, ResourceResolverStrategy resourceResolverStrategy) {
        this.root = root;
        this.resourceResolverStrategy = resourceResolverStrategy;
    }

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case API, CLIENT -> currentProjectNode = node;
            case CLASS -> currentClassNode = node;
            case SQS_LISTENER -> {
                String sourceService = resourceResolverStrategy.resolve((String) currentProjectNode.id, node.value.toString());
                if (sourceService != null) {
                    ServicesGraph.addEdge(ServicesSymbolTable.getId(sourceService), sourceServiceId, 1);
                } else {
                    System.out.println("Parameter not resolved: " + node.value);
                    Integer keyNotResolvedParameter = ServicesSymbolTable.add(node.value.toString(), ServicesSymbolTable.Resource::nonResolved);
                    ServicesGraph.addEdge(keyNotResolvedParameter, sourceServiceId, 1);
                }
            }
            case SQS_SENDER, SNS_SENDER -> {
                Node child = node.children.get(0);
                switch (child.type) {
                    case METHOD_CALL -> {
                        MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) child.value;
                        String destination = new MethodEvaluator(root, (String) currentClassNode.value, methodCallNodeValue).evaluate();
                        String destinationResource = resourceResolverStrategy.resolve((String) currentProjectNode.id, destination);
                        if (destinationResource != null) {
                            ServicesGraph.addEdge(sourceServiceId, ServicesSymbolTable.getId(destinationResource), 1);
                        } else {
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(sourceServiceId, keyNotResolvedParameter, 1);
                        }
                    }
                    case INSTANCE_VARIABLE_ID -> {
                        String variableId = (String) child.value;
                        Node variableDeclarationNode = currentProjectNode.find(variableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                        String destination = (String) variableDeclarationNode.children.get(0).value;
                        String destinationResource = resourceResolverStrategy.resolve((String) currentProjectNode.id, destination);
                        if (destinationResource != null) {
                            ServicesGraph.addEdge(sourceServiceId, ServicesSymbolTable.getId(destinationResource), 1);
                        } else {
                            Integer keyNotResolvedParameter = ServicesSymbolTable.add(destination, ServicesSymbolTable.Resource::nonResolved);
                            ServicesGraph.addEdge(sourceServiceId, keyNotResolvedParameter, 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setSourceServiceId(Integer sourceServiceId) {
        this.sourceServiceId = sourceServiceId;
    }
}
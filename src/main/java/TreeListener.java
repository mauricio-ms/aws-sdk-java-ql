import java.util.function.Consumer;

public class TreeListener implements Consumer<Node> {

    private Node currentProjectNode;

    private Node currentClassNode;

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case PROJECT -> currentProjectNode = node;
            case CLASS -> currentClassNode = node;
            case SQS_LISTENER -> ServicesCommunicationGraph.add(node.value.toString(), currentProjectNode.value.toString());
            case SNS_SENDER -> {
                Node child = node.children.get(0);
                switch (child.type) {
                    case METHOD_CALL -> {
                        MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) child.value;
                        Node propertiesVariableNode = currentProjectNode.find(methodCallNodeValue.caller).parent;
                        Node propertiesNode = currentProjectNode.find(propertiesVariableNode.value, Node.Type.INTERFACE);
                        Node propertiesImplNode = propertiesNode.find(Node.Type.CLASS);
                        String snsTopic = (String) propertiesImplNode.find(methodCallNodeValue.call).children.get(0).value;
                        ServicesCommunicationGraph.add(currentProjectNode.value.toString(), snsTopic);
                    }
                    case INSTANCE_VARIABLE_ID -> {
                        String variableId = (String) child.value;
                        Node variableDeclarationNode = currentProjectNode.find(variableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                        String snsTopic = (String) variableDeclarationNode.children.get(0).value;
                        ServicesCommunicationGraph.add(currentProjectNode.value.toString(), snsTopic);
                    }
                }
            }
        }
    }
}
package tree;

import graph.Node;
import services.ServicesSymbolTable;

public class TreeListenerClient extends TreeListenerDecorator {

    private Node clientNode;

    public TreeListenerClient(TreeListenable treeListenable) {
        super(treeListenable);
    }

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case CLIENT -> clientNode = node;
            case SQS_SENDER, SNS_SENDER -> {
                Node messagingSenderNodePublicInterface = node.top(clientNode);
                if (!messagingSenderNodePublicInterface.type.isJavaType()) {
                    throw new RuntimeException("Unexpected type for a public interface of a Messaging Sender");
                }

                Node messagingSenderNodeExecutor = node.parent;
                if (messagingSenderNodeExecutor.type != Node.Type.METHOD_DECLARATION) {
                    throw new RuntimeException("Unexpected type for an executor of a Messaging Sender");
                }

                String[] messagingSenderNodePublicInterfaceParts = ((String) messagingSenderNodePublicInterface.id).split("\\.");
                String resourceName = messagingSenderNodePublicInterfaceParts[messagingSenderNodePublicInterfaceParts.length - 1] +
                        ":" + messagingSenderNodeExecutor.value;
                Integer resourceId = ServicesSymbolTable.getId(resourceName);
                if (resourceId == null) {
                    resourceId = ServicesSymbolTable.add(resourceName, ServicesSymbolTable.Resource::client);
                }
                setSourceServiceId(resourceId);
            }
            case SQS_LISTENER -> {
                Node listenerNodePublicInterface = node.parent;
                if (!listenerNodePublicInterface.type.isJavaType()) {
                    throw new RuntimeException("Unexpected type for a public interface of a Listener");
                }

                String[] listenerNodePublicInterfaceParts = ((String) listenerNodePublicInterface.id).split("\\.");
                String resourceName = listenerNodePublicInterfaceParts[listenerNodePublicInterfaceParts.length - 1];
                Integer resourceId = ServicesSymbolTable.getId(resourceName);
                if (resourceId == null) {
                    resourceId = ServicesSymbolTable.add(resourceName, ServicesSymbolTable.Resource::client);
                }
                setSourceServiceId(resourceId);
            }
        }

        super.accept(node);
    }
}

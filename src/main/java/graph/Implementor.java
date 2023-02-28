package graph;

public class Implementor {

    private final Node node;

    private final String methodCall;

    public Implementor(Node node, String methodCall) {
        this.node = node;
        this.methodCall = methodCall;
    }

    public Node get() {
        return get(node);
    }

    private Node get(Node currentNode) {
        if (currentNode.value.equals(methodCall)) {
            Node targetNode = currentNode;
            while (targetNode.parent.type != Node.Type.CLASS && targetNode.parent.type != Node.Type.INTERFACE) {
                targetNode = targetNode.parent;
            }
            return targetNode.parent;
        }

        for (Node child : currentNode.children) {
            Node found = get(child);
            if (found != null) {
                return found;
            }
        }

        return null;
    }
}

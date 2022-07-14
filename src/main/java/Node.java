import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Node {

    public Node parent;

    public Object value;

    public Type type;

    public List<Node> children;

    public Node(Object value, Type type) {
        parent = null;
        this.value = value;
        this.type = type;
        children = new ArrayList<>();
    }

    public enum Type {
        PROJECT(0),
        INTERFACE(1),
        CLASS(2),
        INSTANCE_VARIABLE_TYPE(3),

        INSTANCE_VARIABLE_DECLARATION(4),
        INSTANCE_VARIABLE_ID(5),
        METHOD_CALL(6),
        VALUE_ANNOTATION(7),
        SQS_LISTENER(8),
        SQS_SENDER(9),
        SNS_SENDER(10),
        JOIN(11);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public static Type get(int value) {
            return Stream.of(values())
                    .filter(e -> e.value == value)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Type not found for value " + value));
        }
    }

    public void addChild(Node child) {
        children.add(child);
        if (this.type == Type.JOIN) {
            child.parent = this.parent;
        } else {
            child.parent = this;
        }
    }

    public Node find(Object value) {
        return findRec(this, value);
    }

    private Node findRec(Node current, Object value) {
        if (value.equals(current.value)) {
            return current;
        }

        for (Node child : current.children) {
            Node found = findRec(child, value);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    public Node find(Node.Type type) {
        return findRec(this, type);
    }

    private Node findRec(Node current, Node.Type type) {
        if (type.equals(current.type)) {
            return current;
        }

        for (Node child : current.children) {
            Node found = findRec(child, type);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    public Node find(Object value, Node.Type type) {
        return findRec(this, value, type);
    }

    private Node findRec(Node current, Object value, Node.Type type) {
        if (value.equals(current.value) && type.equals(current.type)) {
            return current;
        }

        for (Node child : current.children) {
            Node found = findRec(child, value, type);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    public void walk(Consumer<Node> listener) {
        walkRec(this, listener);
    }

    private void walkRec(Node node, Consumer<Node> listener) {
        listener.accept(node);

        for (Node child : node.children) {
            walkRec(child, listener);
        }
    }

    public void show() {
        showRec(this, "");
    }

    private void showRec(Node node, String tabs) {
        if (node.value == null && node.type == null) {
            StdOut.println("root");
        } else if (node.value != null) {
            StdOut.println("%s%s %s\n", tabs, node.type, node.value);
        }

        for (Node child : node.children) {
            if (node.value != null) {
                showRec(child, tabs + "\t");
            } else {
                showRec(child, tabs);
            }
        }
    }
}

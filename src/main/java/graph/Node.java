package graph;

import java.util.ArrayList;
import java.util.Arrays;
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
        CLOUD_FORMATION_STACK_SYMBOLS_TABLE(0),
        CLOUD_FORMATION_CLIENT_SYMBOLS_TABLE(1),
        PROJECT(2),
        LIB(3),
        INTERFACE(4),
        CLASS(5),
        INSTANCE_VARIABLE_TYPE(6),
        INSTANCE_VARIABLE_DECLARATION(7),
        INSTANCE_VARIABLE_ID(8),
        METHOD_CALL(9),
        VALUE_ANNOTATION(10),
        SQS_LISTENER(11),
        SQS_SENDER(12),
        SNS_SENDER(13),
        JOIN(14);

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

    public Node findUnique(Node.Type type) {
        List<Node> nodes = find(type);
        return !nodes.isEmpty() ? nodes.get(0) : null;
    }

    public List<Node> find(Node.Type type) {
        return findRec(this, type);
    }

    private List<Node> findRec(Node current, Node.Type type) {
        List<Node> nodes = new ArrayList<>();
        if (type.equals(current.type)) {
            nodes.add(current);
        }

        for (Node child : current.children) {
            nodes.addAll(findRec(child, type));
        }

        return nodes;
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

    public void walk(Consumer<Node> listener, Node.Type... typesToSkip) {
        walkRec(this, Arrays.stream(typesToSkip).toList(), listener);
    }

    private void walkRec(Node node, List<Node.Type> typesToSkip, Consumer<Node> listener) {
        listener.accept(node);

        for (Node child : node.children) {
            if (!typesToSkip.contains(child.type)) {
                walkRec(child, typesToSkip, listener);
            }
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

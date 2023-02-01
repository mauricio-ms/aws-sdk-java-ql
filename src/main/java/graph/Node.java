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
        CONSTRUCTOR(6),
        INSTANCE_VARIABLE_TYPE(7),
        INSTANCE_VARIABLE_DECLARATION(8),
        INSTANCE_VARIABLE_ID(9),
        METHOD_CALL(10),
        METHOD_DECLARATION(11),
        RETURN_EXPRESSION(12),
        VALUE_ANNOTATION(13),
        SQS_LISTENER(14),
        SQS_SENDER(15),
        SNS_SENDER(16);

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

    public Node root() {
        Node temp = this;
        while (temp.parent != null) {
            temp = temp.parent;
        }
        return temp;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public Node findUpwards(Node.Type... types) {
        List<Type> typesList = Arrays.stream(types).toList();
        Node temp = this;
        while (temp != null) {
            if (typesList.contains(temp.type)) {
                return temp;
            }
            temp = temp.parent;
        }
        return null;
    }

    public Node findUnique(Node.Type... types) {
        for (Node.Type type : types) {
            List<Node> nodes = find(type);
            if (!nodes.isEmpty()) {
                return nodes.get(0);
            }
        }
        return null;
    }

    public Node getChild(Node.Type... types) {
        List<Type> typesList = Arrays.stream(types).toList();
        for (Node child : children) {
            if (typesList.contains(child.type)) {
                return child;
            }
        }
        return null;
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

    public Node find(Object value, Node.Type... types) {
        return findRec(this, value, List.of(types));
    }

    private Node findRec(Node current, Object value, List<Node.Type> types) {
        if (value.equals(current.value) && (types.isEmpty() || types.contains(current.type))) {
            return current;
        }

        for (Node child : current.children) {
            Node found = findRec(child, value, types);
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

    @Override
    public String toString() {
        return type + ":" + value;
    }
}

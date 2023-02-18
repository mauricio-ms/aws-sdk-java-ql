package tree;

import graph.Node;

import java.util.function.Consumer;

public interface TreeListenable extends Consumer<Node> {

    void setSourceServiceId(Integer sourceServiceId);
}
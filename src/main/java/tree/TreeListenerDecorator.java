package tree;

import graph.Node;

public class TreeListenerDecorator implements TreeListenable {

    private final TreeListenable treeListenable;

    public TreeListenerDecorator(TreeListenable treeListenable) {
        this.treeListenable = treeListenable;
    }

    @Override
    public void accept(Node node) {
        treeListenable.accept(node);
    }

    @Override
    public void setSourceServiceId(Integer sourceServiceId) {
        treeListenable.setSourceServiceId(sourceServiceId);
    }
}

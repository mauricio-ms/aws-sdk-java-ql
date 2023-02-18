package tree;

import graph.Node;
import services.ServicesSymbolTable;

public class TreeListenerProject extends TreeListenerDecorator {

    public TreeListenerProject(TreeListenable treeListenable) {
        super(treeListenable);
    }

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        if (node.type == Node.Type.API) {
            setSourceServiceId(ServicesSymbolTable.getId(node.value.toString()));
        }

        super.accept(node);
    }
}

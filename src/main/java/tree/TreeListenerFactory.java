package tree;

import graph.Node;
import graph.SsmParameter;

public final class TreeListenerFactory {

    private TreeListenerFactory() {
    }

    public static TreeListenable project(Node tree) {
        return new TreeListenerProject(
                new TreeListener(tree,
                        (projectId, resource) -> new SsmParameter(tree.find(projectId, Node.Type.API), resource)
                                .resolve()));
    }

    public static TreeListenable client(Node clientTree, Node projectTree) {
        return new TreeListenerClient(new TreeListener(clientTree,
                (projectId, resource) -> new SsmParameter(projectTree.find(projectId, Node.Type.API), resource)
                        .resolve()));
    }
}

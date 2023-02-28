package tree;

import graph.Implementor;
import graph.MethodCallNodeValue;
import graph.Node;
import graph.QualifiedClassName;
import services.ServicesSymbolTable;

public class TreeListenerDependencies extends TreeListenerDecorator {

    private final Node clientTree;

    public TreeListenerDependencies(TreeListenable treeListenable, Node clientTree) {
        super(treeListenable);
        this.clientTree = clientTree;
    }

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case API -> setSourceServiceId(ServicesSymbolTable.getId((String) node.id));
            case METHOD_CALL -> {
                MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) node.value;
                String caller = methodCallNodeValue.caller;
                Node classNode = node.findUpwards(Node.Type.CLASS);
                Node instanceVariableDeclarationNode = classNode.find(caller, Node.Type.INSTANCE_VARIABLE_DECLARATION);
                if (instanceVariableDeclarationNode == null) {
                    return;
                }
                QualifiedClassName qualifiedClassName = new QualifiedClassName(classNode, caller.substring(0, 1).toUpperCase() + caller.substring(1));
                Node callerNode = clientTree.find(qualifiedClassName.get(), Node.Type.INTERFACE, Node.Type.CLASS);
                if (callerNode == null) {
                    return;
                }
                Node implementorNode = new Implementor(callerNode, methodCallNodeValue.call).get();
                if (implementorNode == null) {
                    return;
                }
                Node clientNode = callerNode.findUpwards(Node.Type.CLIENT);
                super.accept(clientNode);

                Node callNode = implementorNode.find(methodCallNodeValue.call, Node.Type.METHOD_DECLARATION);
                Node messagingNode = callNode.findUnique(Node.Type.SQS_SENDER, Node.Type.SNS_SENDER);
                super.accept(messagingNode);
            }
        }
    }
}

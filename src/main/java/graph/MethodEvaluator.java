package graph;

public class MethodEvaluator {

    private final Node tree;

    private final String sourceClass;

    private final MethodCallNodeValue methodCallNodeValue;

    public MethodEvaluator(Node tree, String sourceClass, MethodCallNodeValue methodCallNodeValue) {
        this.tree = tree;
        this.sourceClass = sourceClass;
        this.methodCallNodeValue = methodCallNodeValue;
    }

    public void evaluate() {
        Node sourceClassNode = tree.find(sourceClass, Node.Type.CLASS);
        Node callerIdNode = sourceClassNode.find(methodCallNodeValue.caller, Node.Type.INSTANCE_VARIABLE_DECLARATION);
        Node callerTypeNode = callerIdNode.parent;
        if (callerTypeNode.type != Node.Type.INSTANCE_VARIABLE_TYPE) {
            throw new RuntimeException("Unexpected node type");
        }
        // FIXME package hardcoded
        Node callerClassNode = tree.find("com.beatstars.inventory.common.service." + callerTypeNode.value, Node.Type.INTERFACE, Node.Type.CLASS);
        evaluateFromCaller(sourceClassNode, callerClassNode);
    }

    private void evaluateFromCaller(Node sourceClassNode, Node callerClassNode) {
        System.out.println(callerClassNode);
        Node methodCallNode = tree.find(methodCallNodeValue, Node.Type.METHOD_CALL);
        System.out.println(methodCallNode);
        Node implementor = implementor(callerClassNode);
        System.out.println(implementor);
        Node methodDeclarationNode = implementor.find(methodCallNodeValue.call, Node.Type.METHOD_DECLARATION);
        System.out.println(methodDeclarationNode);
        String returnExpression = (String) methodDeclarationNode.findUnique(Node.Type.RETURN_EXPRESSION).value;
        Node instanceVariableOwner = instanceVariableOwner(returnExpression, implementor);
        System.out.println(instanceVariableOwner);
        String evaluatedInstanceVariable = evaluateInstanceVariable(returnExpression, instanceVariableOwner);
        System.out.println(evaluatedInstanceVariable);
        boolean isSsmParameter = evaluatedInstanceVariable.startsWith("${") && evaluatedInstanceVariable.endsWith("}");
        if (isSsmParameter) {
            SsmParameter ssmParameter = new SsmParameter(sourceClassNode.findUpwards(Node.Type.PROJECT, Node.Type.LIB), evaluatedInstanceVariable);
            String ssmParameterValue = ssmParameter.resolve();
            // FIXME TO WORKS, THE INVENTORY CLIENT HAS TO BE PROCESSED AS THE REAL STACK FILES
            System.out.println(ssmParameterValue);
        }
    }

    private String evaluateInstanceVariable(String instanceVariableId, Node currentNode) {
        Node constructorNode = currentNode.getChild(Node.Type.CONSTRUCTOR);
        ConstructorNodeValue constructorNodeValue = (ConstructorNodeValue) constructorNode.value;
        Evaluable parameter = constructorNodeValue.get(instanceVariableId);
        String parameterEvaluated = parameter.evaluate();
        if (!parameterEvaluated.equals(instanceVariableId)) {
            return parameterEvaluated;
        } else {
            Node subClassNode = currentNode.getChild(Node.Type.INTERFACE, Node.Type.CLASS);
            if (subClassNode != null) {
                return evaluateInstanceVariable(instanceVariableId, subClassNode);
            } else {
                return null;
            }
        }
    }

    private Node instanceVariableOwner(String instanceVariableId, Node currentNode) {
        Node instanceVariableDeclarationNode = currentNode.find(instanceVariableId, Node.Type.INSTANCE_VARIABLE_DECLARATION);
        if (instanceVariableDeclarationNode != null) {
            return currentNode;
        }


        Node targetNode = currentNode;
        while (targetNode.parent.type == Node.Type.CLASS || targetNode.parent.type == Node.Type.INTERFACE) {
            Node instanceVariableDeclarationParentNode = instanceVariableOwner(instanceVariableId, targetNode.parent);
            if (instanceVariableDeclarationParentNode != null) {
                return instanceVariableDeclarationParentNode;
            }
            targetNode = targetNode.parent;
        }

        return null;
    }

    private Node implementor(Node currentNode) {
        if (currentNode.value.equals(methodCallNodeValue.call)) {
            Node targetNode = currentNode;
            while (targetNode.parent.type != Node.Type.CLASS && targetNode.parent.type != Node.Type.INTERFACE) {
                targetNode = targetNode.parent;
            }
            return targetNode.parent;
        }

        for (Node child : currentNode.children) {
            Node found = implementor(child);
            if (found != null) {
                return found;
            }
        }

        return null;
    }
}
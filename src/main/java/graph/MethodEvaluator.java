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

    public String evaluate() {
        try {
            Node sourceClassNode = tree.find(sourceClass, Node.Type.CLASS);
            Node callerIdNode = sourceClassNode.find(methodCallNodeValue.caller, Node.Type.INSTANCE_VARIABLE_DECLARATION);
            Node callerTypeNode = callerIdNode.parent; 
            if (callerTypeNode.type != Node.Type.INSTANCE_VARIABLE_TYPE) {
                throw new RuntimeException("Unexpected node type");
            }
            String qualifiedClassName = qualifiedClassName(sourceClassNode, (String) callerTypeNode.value);
            Node callerClassNode = tree.find(qualifiedClassName, Node.Type.INTERFACE, Node.Type.CLASS);
            return evaluateFromCaller(sourceClassNode, callerClassNode);
        } catch (Exception e) {
            // TODO when is not possible to evaluate: put the expression, like: String.format("%s-core-audio-%s-sqs", CommonConfig.env, step.getQueueName())
            return methodCallNodeValue.caller + "#" + methodCallNodeValue.call;
        }
    }

    private String qualifiedClassName(Node sourceClassNode, String simpleClassName) {
        Node importDeclarationNode = sourceClassNode.find(simpleClassName, Node.Type.IMPORT_DECLARATION);
        if (importDeclarationNode != null) {
            return (String) importDeclarationNode.value;
        }
        String sourceClassFullyQualifiedName = (String) sourceClassNode.value;
        String sourceClassPackage = sourceClassFullyQualifiedName.substring(0, sourceClassFullyQualifiedName.lastIndexOf("."));
        return sourceClassPackage + "." + simpleClassName;
    }

    private String evaluateFromCaller(Node sourceClassNode, Node callerClassNode) {
        Node implementor = implementor(callerClassNode);
        if (implementor == null) {
            throw new RuntimeException("Implementor not found");
        }

        Node methodDeclarationNode = implementor.find(methodCallNodeValue.call, Node.Type.METHOD_DECLARATION);
        String returnExpression = (String) methodDeclarationNode.findUnique(Node.Type.RETURN_EXPRESSION).value;
        Node instanceVariableOwner = instanceVariableOwner(returnExpression, implementor);
        if (instanceVariableOwner == null) {
            throw new RuntimeException("InstanceVariable owner not found");
        }

        String evaluatedInstanceVariable = evaluateInstanceVariable(returnExpression, instanceVariableOwner);
        if (evaluatedInstanceVariable == null) {
            throw new RuntimeException("The instance variable '" + returnExpression + "' could not be evaluated for the node " + instanceVariableOwner);
        }
        return evaluatedInstanceVariable;
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
}
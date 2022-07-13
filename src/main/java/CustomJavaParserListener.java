import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomJavaParserListener extends JavaParserBaseListener {

    private final Node nodeProject;

    private Node classNode;

    private final List<String> notificationMessageTemplateVariables;

    private final Map<String, String> snsVariableToTopicMap;

    public CustomJavaParserListener(Node nodeProject) {
        this.nodeProject = nodeProject;
        notificationMessageTemplateVariables = new ArrayList<>();
        snsVariableToTopicMap = new HashMap<>();
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        classNode = new Node(ctx.identifier().getText(), Node.Type.CLASS);

        if (ctx.IMPLEMENTS() != null) {
            Node joinNode = new Node(null, Node.Type.JOIN);
            for (var interfaceType : ctx.typeList()) {
                Node interfaceNode = new Node(interfaceType.getText(), Node.Type.INTERFACE);
                nodeProject.addChild(interfaceNode);
                interfaceNode.addChild(joinNode);
            }
            joinNode.addChild(classNode);
        } else {
            nodeProject.addChild(classNode);
        }
    }

    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        classNode = new Node(ctx.identifier().getText(), Node.Type.CLASS);
    }

    @Override
    public void enterMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
        var modifier = ctx.getParent().getChild(JavaParser.ModifierContext.class, 0);
        if (modifier != null) {
            var annotation = modifier.classOrInterfaceModifier().annotation();
            if (annotation != null) {
                switch (annotation.qualifiedName().getText()) {
                    case "Value" -> snsVariableToTopicMap.put(ctx.fieldDeclaration().variableDeclarators().getText(), requireAnnotationValue(annotation));
                    case "SqsListener" -> classNode.addChild(new Node(requireAnnotationValue(annotation), Node.Type.SQS_LISTENER));
//                    case "SqsListener" -> ServicesCommunicationGraph.add(requireAnnotationValue(annotation), nodeProject.value.toString());
                }
            }
        }
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        var type = getType(ctx.typeType());
        Node fieldNode = classNode.find(type);
        if (fieldNode == null) {
            fieldNode = new Node(type, Node.Type.INSTANCE_VARIABLE_TYPE);
            classNode.addChild(fieldNode);
        }
        fieldNode.addChild(new Node(ctx.variableDeclarators().getText(), Node.Type.INSTANCE_VARIABLE_DECLARATION));

        if ("NotificationMessagingTemplate".equals(type)) {
            notificationMessageTemplateVariables.add(ctx.variableDeclarators().getText());
        }
    }

    private String getType(JavaParser.TypeTypeContext ctx) {
        var nonPrimitiveType = ctx.classOrInterfaceType();
        if (nonPrimitiveType != null) {
            return nonPrimitiveType.getText();
        } else {
            return ctx.primitiveType().getText();
        }
    }

    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        for (var modifier : ctx.variableModifier()) {
            var annotation = modifier.annotation();
            if (annotation != null && "Value".equals(annotation.qualifiedName().getText())) {
                Node instanceVariableNode = classNode.find(ctx.variableDeclaratorId().getText());
                // The formal parameter being analyzed can be a @Value parameter that is not an instance variable
                if (instanceVariableNode != null) {
                    instanceVariableNode.addChild(new Node(requireAnnotationValue(annotation), Node.Type.VALUE_ANNOTATION));
                    snsVariableToTopicMap.put(ctx.variableDeclaratorId().getText(), requireAnnotationValue(annotation));
                }
            }
        }
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        String caller = ctx.getParent().getChild(0).getText();
        Node nodeCaller = nodeProject.find(caller);
        if (nodeCaller != null && nodeCaller.parent.value.equals("NotificationMessagingTemplate")) {
            Node nodeSnsSender = new Node(ctx.identifier().getText(), Node.Type.SNS_SENDER);
            nodeCaller.addChild(nodeSnsSender);
            var arguments = ctx.expressionList();
            if (arguments != null) {
                var firstArgument = arguments.expression(0);
                var methodCall = firstArgument.methodCall();
                if (methodCall != null) {
                    var propertiesMethodCall = removeGetJavaBean(methodCall.identifier().getText());
                    var propertiesMethodCaller = firstArgument.expression(0).getText();
                    nodeSnsSender.addChild(new Node(new MethodCallNodeValue(propertiesMethodCaller, propertiesMethodCall), Node.Type.METHOD_CALL));
                } else {
                    nodeSnsSender.addChild(new Node(firstArgument.getText(), Node.Type.INSTANCE_VARIABLE_ID));
                }
            }
        }
        if (notificationMessageTemplateVariables.contains(caller)) {
            snsSendNotification(ctx);
        }
    }

    private String removeGetJavaBean(String methodCall) {
        if (methodCall.startsWith("get")) {
            String substring = methodCall.substring(3);
            return Character.toLowerCase(substring.charAt(0)) + substring.substring(1);
        }
        return methodCall;
    }

    private void snsSendNotification(JavaParser.MethodCallContext ctx) {
        var arguments = ctx.expressionList();
        var firstArgument = arguments.expression(0);
        String snsTopic = snsVariableToTopicMap.get(firstArgument.getText());
        if (snsTopic == null) {
//            throw new RuntimeException("Unknown 'NotificationMessageTemplate' API utilization. Check for updates javadoc.io.");
        } else {
//            ServicesCommunicationGraph.add(nodeProject.value.toString(), snsTopic);
        }
    }

    private String requireAnnotationValue(JavaParser.AnnotationContext ctx) {
        var elementValuePairs = ctx.elementValuePairs();
        if (elementValuePairs != null) {
            for (var elementValue : elementValuePairs.elementValuePair()) {
                if ("value".equals(elementValue.identifier().getText())) {
                    return elementValue.elementValue().getText().replace("\"", "");
                }
            }
            throw new RuntimeException("Syntax error: The parameter 'value' is required for this annotation.");
        } else {
            return ctx.elementValue().getText().replace("\"", "");
        }
    }
}

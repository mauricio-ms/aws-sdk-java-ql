import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomJavaParserListener extends JavaParserBaseListener {

    private final String service;

    private final List<String> notificationMessageTemplateVariables;

    private final Map<String, String> snsVariableToTopicMap;

    public CustomJavaParserListener(String service) {
        this.service = service;
        notificationMessageTemplateVariables = new ArrayList<>();
        snsVariableToTopicMap = new HashMap<>();
    }

    @Override
    public void enterMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
        var modifier = ctx.getParent().getChild(JavaParser.ModifierContext.class, 0);
        if (modifier != null) {
            var annotation = modifier.classOrInterfaceModifier().annotation();
            if (annotation != null) {
                switch (annotation.qualifiedName().getText()) {
                    case "Value" -> snsVariableToTopicMap.put(ctx.fieldDeclaration().variableDeclarators().getText(), requireAnnotationValue(annotation));
                    case "SqsListener" -> ServicesCommunicationGraph.add(requireAnnotationValue(annotation), service);
                }
            }
        }
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        var type = ctx.typeType();
        var nonPrimitiveType = type.classOrInterfaceType();
        if (nonPrimitiveType != null && "NotificationMessagingTemplate".equals(nonPrimitiveType.getText())) {
            notificationMessageTemplateVariables.add(ctx.variableDeclarators().getText());
        }
    }

    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        for (var modifier : ctx.variableModifier()) {
            var annotation = modifier.annotation();
            if (annotation != null && "Value".equals(annotation.qualifiedName().getText())) {
                snsVariableToTopicMap.put(ctx.variableDeclaratorId().getText(), requireAnnotationValue(annotation));
            }
        }
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        if (notificationMessageTemplateVariables.contains(ctx.getParent().getChild(0).getText())) {
            snsSendNotification(ctx);
        }
    }

    private void snsSendNotification(JavaParser.MethodCallContext ctx) {
        var arguments = ctx.expressionList();
        var firstArgument = arguments.expression(0);
        System.out.println(firstArgument.getText());
        String snsTopic = snsVariableToTopicMap.get(firstArgument.getText());
        if (snsTopic == null) {
            throw new RuntimeException("Unknown 'NotificationMessageTemplate' API utilization. Check for updates javadoc.io.");
        } else {
            ServicesCommunicationGraph.add(service, snsTopic);
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

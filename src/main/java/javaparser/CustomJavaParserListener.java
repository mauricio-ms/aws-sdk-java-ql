package javaparser;

import graph.MethodCallNodeValue;
import graph.Node;
import services.DependenciesSymbolTable;
import services.ServiceMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomJavaParserListener extends JavaParserBaseListener {

    private String packageDeclaration;

    private final Map<String, String> imports;

    private final Node nodeProject;

    private Node classNode;

    public CustomJavaParserListener(Node nodeProject) {
        imports = new HashMap<>();
        this.nodeProject = nodeProject;
    }

    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        packageDeclaration = ctx.qualifiedName().getText();
        if (ServiceMetadata.basePackage == null) {
            ServiceMetadata.basePackage = packageDeclaration;
        }
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        var qualifiedName = ctx.qualifiedName();
        var identifiers = qualifiedName.identifier();
        imports.put(identifiers.get(identifiers.size()-1).getText(), qualifiedName.getText());
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        classNode = new Node(packageDeclaration + "." + ctx.identifier().getText(), Node.Type.CLASS);
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

        if (ctx.EXTENDS() != null) {
            var baseType = ctx.typeType();
            String baseTypeQualifiedName = imports.get(baseType.classOrInterfaceType().identifier(0).getText());
            if (baseTypeQualifiedName != null) {
                if (!ServiceMetadata.typeBelongsToService(baseTypeQualifiedName)) {
                    DependenciesSymbolTable.add((String) classNode.value, baseTypeQualifiedName);
                }
            }
        }
    }

    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        classNode = new Node(ctx.identifier().getText(), Node.Type.CLASS);
    }

    @Override
    public void enterAnnotation(JavaParser.AnnotationContext ctx) {
        if ("SqsListener".equals(ctx.qualifiedName().getText())) {
            classNode.addChild(new Node(requireAnnotationValue(ctx), Node.Type.SQS_LISTENER));
        }
    }

    @Override
    public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
        var memberDeclaration = ctx.memberDeclaration();
        if (memberDeclaration == null) {
            return;
        }
        var fieldDeclaration = memberDeclaration.fieldDeclaration();
        if (fieldDeclaration == null) {
            return;
        }

        var type = getType(fieldDeclaration.typeType());
        Node fieldNode = classNode.find(type);
        if (fieldNode == null) {
            fieldNode = new Node(type, Node.Type.INSTANCE_VARIABLE_TYPE);
            classNode.addChild(fieldNode);
        }
        Node instanceVariableNode = new Node(fieldDeclaration.variableDeclarators().getText(), Node.Type.INSTANCE_VARIABLE_DECLARATION);
        fieldNode.addChild(instanceVariableNode);
        Node valueAnnotationNode = getValueAnnotationNode(ctx.modifier());
        if (valueAnnotationNode != null) {
            instanceVariableNode.addChild(valueAnnotationNode);
        }
    }

    private Node getValueAnnotationNode(List<JavaParser.ModifierContext> modifiers) {
        if (modifiers == null) {
            return null;
        }

        for (var modifier : modifiers) {
            var classOrInterfaceModifier = modifier.classOrInterfaceModifier();
            if (classOrInterfaceModifier == null) {
                continue;
            }
            var annotation = classOrInterfaceModifier.annotation();
            if (annotation != null && "Value".equals(annotation.qualifiedName().getText())) {
                return new Node(requireAnnotationValue(annotation), Node.Type.VALUE_ANNOTATION);
            }
        }

        return null;
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
                }
            }
        }
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        String caller = ctx.getParent().getChild(0).getText();
        Node nodeCaller = nodeProject.find(caller);
        Node.Type messagingNodeType = getMessagingNodeType(nodeCaller);
        if (messagingNodeType == null) {
            return;
        }

        Node messagingNode = new Node(ctx.identifier().getText(), messagingNodeType);
        nodeCaller.addChild(messagingNode);
        var arguments = ctx.expressionList();
        if (arguments != null) {
            var firstArgument = arguments.expression(0);
            var methodCall = firstArgument.methodCall();
            if (methodCall != null) {
                var propertiesMethodCall = removeGetJavaBean(methodCall.identifier().getText());
                var propertiesMethodCaller = firstArgument.expression(0).getText();
                messagingNode.addChild(new Node(new MethodCallNodeValue(propertiesMethodCaller, propertiesMethodCall), Node.Type.METHOD_CALL));
            } else {
                messagingNode.addChild(new Node(firstArgument.getText(), Node.Type.INSTANCE_VARIABLE_ID));
            }
        }
    }

    private Node.Type getMessagingNodeType(Node nodeCaller) {
        if (nodeCaller == null) {
            return null;
        }

        return switch ((String) nodeCaller.parent.value) {
            case "QueueMessagingTemplate" -> Node.Type.SQS_SENDER;
            case "NotificationMessagingTemplate" -> Node.Type.SNS_SENDER;
            default -> null;
        };
    }

    private String removeGetJavaBean(String methodCall) {
        if (methodCall.startsWith("get")) {
            String substring = methodCall.substring(3);
            return Character.toLowerCase(substring.charAt(0)) + substring.substring(1);
        }
        return methodCall;
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

package javaparser;

import graph.ConstructorNodeValue;
import graph.Evaluable;
import graph.MethodCallNodeValue;
import graph.Node;
import org.antlr.v4.runtime.RuleContext;
import services.DependenciesSymbolTable;
import services.ServiceMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomJavaParserListener extends JavaParserBaseListener {

    private String packageDeclaration;

    private final Map<String, String> imports;

    private final Map<String, String> instanceVariables;

    private final Node nodeProject;

    private String qualifiedClassName;

    public CustomJavaParserListener(Node nodeProject) {
        imports = new HashMap<>();
        instanceVariables = new HashMap<>();
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
        imports.put(identifiers.get(identifiers.size() - 1).getText(), qualifiedName.getText());
    }

    @Override
    public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        nodeProject.addChild(new Node(packageDeclaration + "." + ctx.identifier().getText(), Node.Type.INTERFACE));
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        qualifiedClassName = packageDeclaration + "." + ctx.identifier().getText();
        Node classNode = new Node(qualifiedClassName, Node.Type.CLASS);
        if (ctx.IMPLEMENTS() == null && ctx.EXTENDS() == null) {
            nodeProject.addChild(classNode);
            return;
        }

        if (ctx.IMPLEMENTS() != null) {
            for (var type : ctx.typeList()) {
                String nodeValue = imports.getOrDefault(type.getText(), packageDeclaration + "." + type.getText());
                var interfaceNode = nodeProject.find(nodeValue, Node.Type.INTERFACE);
                if (interfaceNode == null) {
                    throw new RuntimeException("Interface node not found for " + nodeValue);
                }
                // TODO FIX TO USE TYPE VALUE
                interfaceNode.addChild(new Node(packageDeclaration + "." + ctx.identifier().getText(), Node.Type.CLASS));
            }
        }

        if (ctx.EXTENDS() != null) {
            var baseType = ctx.typeType();
            String baseTypeQualifiedName = imports.get(baseType.classOrInterfaceType().identifier(0).getText());
            if (baseTypeQualifiedName != null && !ServiceMetadata.typeBelongsToService(baseTypeQualifiedName)) {
                nodeProject.addChild(classNode);
                DependenciesSymbolTable.add((String) classNode.value, baseTypeQualifiedName);
            } else {
                String nodeValue = imports.getOrDefault(baseType.getText(), packageDeclaration + "." + baseType.getText());
                var baseClassNode = nodeProject.find(nodeValue, Node.Type.CLASS);
                if (baseClassNode == null) {
                    throw new RuntimeException("Base Class node not found for " + nodeValue);
                }
                baseClassNode.addChild(new Node(packageDeclaration + "." + ctx.identifier().getText(), Node.Type.CLASS));
            }
        }
    }

    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        qualifiedClassName = packageDeclaration + "." + ctx.identifier().getText();
    }

    @Override
    public void enterAnnotation(JavaParser.AnnotationContext ctx) {
        if ("SqsListener".equals(ctx.qualifiedName().getText())) {
            nodeProject.find(qualifiedClassName, Node.Type.CLASS)
                    .addChild(new Node(requireAnnotationValue(ctx), Node.Type.SQS_LISTENER));
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
        Node classNode = nodeProject.find(qualifiedClassName, Node.Type.CLASS);
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

    @Override
    public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        Node nodeClass = nodeProject.find(packageDeclaration + "." + ctx.identifier().getText(), Node.Type.CLASS);
        Map<String, Evaluable> parameters = ctx.formalParameters().formalParameterList().formalParameter()
                .stream()
                .map(Evaluable::annotatedValueFrom)
                .collect(Collectors.toMap(Evaluable::id, Function.identity()));
        Node nodeConstructor = new Node(new ConstructorNodeValue(parameters, getSuperParameters(ctx)), Node.Type.CONSTRUCTOR);
        nodeClass.addChild(nodeConstructor);
    }

    private List<String> getSuperParameters(JavaParser.ConstructorDeclarationContext ctx) {
        for (var blockStatement : ctx.constructorBody.blockStatement()) {
            var methodCall = blockStatement.statement().statementExpression.methodCall();
            if (methodCall != null && methodCall.SUPER() != null) {
                return methodCall.expressionList()
                        .expression()
                        .stream()
                        .map(RuleContext::getText)
                        .toList();
            }
        }
        return null;
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
                Node instanceVariableNode = nodeProject.find(qualifiedClassName, Node.Type.CLASS)
                        .find(ctx.variableDeclaratorId().getText());
                // The formal parameter being analyzed can be a @Value parameter that is not an instance variable
                if (instanceVariableNode != null) {
                    instanceVariableNode.addChild(new Node(requireAnnotationValue(annotation), Node.Type.VALUE_ANNOTATION));
                }
            }
        }
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        Node classNode = nodeProject.find(qualifiedClassName, Node.Type.CLASS);
        String key = String.format("%s-%s-%s", nodeProject, classNode, ctx.getText());
        System.out.println(key);
        String caller = ctx.getParent().getChild(0).getText();
        Node nodeCaller = getCallerNode(caller);
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
                var propertiesMethodCall = methodCall.identifier().getText();
                var propertiesMethodCaller = firstArgument.expression(0).getText();
                messagingNode.addChild(new Node(new MethodCallNodeValue(propertiesMethodCaller, propertiesMethodCall), Node.Type.METHOD_CALL));
            } else {
                messagingNode.addChild(new Node(firstArgument.getText(), Node.Type.INSTANCE_VARIABLE_ID));
            }
        }
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        System.out.println("enterMethodDeclaration=" + ctx.getText());
        if (ctx.typeTypeOrVoid() == null) {
            return;
        }

        Node methodDeclarationNode = new Node(ctx.identifier().getText(), Node.Type.METHOD_DECLARATION);
        nodeProject.find(qualifiedClassName, Node.Type.CLASS)
                .addChild(methodDeclarationNode);

        var blockStatements = ctx.methodBody().block().blockStatement();
        var returnStatement = blockStatements
                .get(blockStatements.size() - 1)
                .statement();
        String returnExpression = returnStatement.expression(0).getText();
        methodDeclarationNode.addChild(new Node(returnExpression, Node.Type.RETURN_EXPRESSION));
    }

    private Node getCallerNode(String caller) {
        String parsedCaller = caller.startsWith("this.") ? caller.substring(5) : caller;
        return nodeProject.find(parsedCaller);
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

package graph;

import javaparser.JavaParser;

public abstract class Evaluable {

    final String id;

    public static Evaluable annotatedValueFrom(JavaParser.FormalParameterContext ctx) {
        String id = ctx.variableDeclaratorId().getText();
        for (var modifier : ctx.variableModifier()) {
            var annotation = modifier.annotation();
            if (annotation != null && "Value".equals(annotation.qualifiedName().getText())) {
                return AnnotatedValue.from(id, annotation);
            }
        }

        return new SimpleValue(id);
    }

    Evaluable(String id) {
        this.id = id;
    }

    public abstract String evaluate();

    public String id() {
        return id;
    }
}
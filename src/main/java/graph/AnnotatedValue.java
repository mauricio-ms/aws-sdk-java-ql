package graph;

import javaparser.JavaParser;

class AnnotatedValue extends Evaluable {

    private final String annotationValue;

    public static AnnotatedValue from(String id, JavaParser.AnnotationContext ctx) {
        var elementValuePairs = ctx.elementValuePairs();
        if (elementValuePairs != null) {
            for (var elementValue : elementValuePairs.elementValuePair()) {
                if ("value".equals(elementValue.identifier().getText())) {
                    return new AnnotatedValue(id, elementValue.elementValue().getText().replace("\"", ""));
                }
            }
            throw new RuntimeException("Syntax error: The parameter 'value' is required for this annotation.");
        } else {
            return new AnnotatedValue(id, ctx.elementValue().getText().replace("\"", ""));
        }
    }

    protected AnnotatedValue(String id, String annotationValue) {
        super(id);
        this.annotationValue = annotationValue;
    }

    @Override
    public String evaluate() {
        return annotationValue;
    }

    @Override
    public String toString() {
        return "AnnotatedValue{" +
                "annotationValue='" + annotationValue + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

package graph;

public class QualifiedClassName {

    private final Node sourceClassNode;

    private final String simpleClassName;

    public QualifiedClassName(Node sourceClassNode, String simpleClassName) {
        this.sourceClassNode = sourceClassNode;
        this.simpleClassName = simpleClassName;
    }

    public String get() {
        Node importDeclarationNode = sourceClassNode.find(simpleClassName, Node.Type.IMPORT_DECLARATION);
        if (importDeclarationNode != null) {
            return (String) importDeclarationNode.value;
        }
        String sourceClassFullyQualifiedName = (String) sourceClassNode.value;
        String sourceClassPackage = sourceClassFullyQualifiedName.substring(0, sourceClassFullyQualifiedName.lastIndexOf("."));
        return sourceClassPackage + "." + simpleClassName;
    }
}

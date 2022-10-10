package services;

public final class ServiceMetadata {

    public static String basePackage;

    private ServiceMetadata() {
    }

    public static boolean typeBelongsToService(String typeQualifiedName) {
        return typeQualifiedName != null && typeQualifiedName.startsWith(basePackage);
    }
}

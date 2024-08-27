package dev.lapinski.persinate.generate.model;

public record JavaExtends(JavaQualifiedName className) implements JavaCode {
    @Override
    public String javaCode() {
        if (className.equals(JavaQualifiedName.EMPTY)) {
            return "";
        }

        return "extends " + className.javaCode();
    }

    public static final JavaExtends EMPTY = new JavaExtends(JavaQualifiedName.EMPTY);

    public static JavaExtends fromClass(Class<?> klass) {
        return new JavaExtends(JavaQualifiedName.fromClass(klass));
    }
}

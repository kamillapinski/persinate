package dev.lapinski.persinate.generate.model;

public record JavaRawBlock(String javaCode) implements JavaClassField {
    @Override
    public JavaAnnotations annotations() {
        return JavaAnnotations.EMPTY;
    }

    @Override
    public ClassFieldModifiers modifiers() {
        return ClassFieldModifiers.DEFAULT;
    }

    @Override
    public JavaQualifiedName returnType() {
        return JavaQualifiedName.EMPTY;
    }

    public static final JavaRawBlock EMPTY = new JavaRawBlock("");
}

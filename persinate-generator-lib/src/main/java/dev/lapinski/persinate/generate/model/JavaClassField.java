package dev.lapinski.persinate.generate.model;

public interface JavaClassField extends JavaCode {
    JavaAnnotations annotations();
    ClassFieldModifiers modifiers();
    JavaQualifiedName returnType();
}

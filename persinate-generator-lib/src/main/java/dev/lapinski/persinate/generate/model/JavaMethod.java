package dev.lapinski.persinate.generate.model;

public record JavaMethod(
    String name,
    JavaQualifiedName returnType,
    ClassFieldModifiers modifiers,
    JavaAnnotations annotations,
    JavaArguments arguments,
    String code
) implements JavaClassField {
    @Override
    public String javaCode() {
        String annotationsBlock = "";
        if (!annotations.annotations().isEmpty()) {
            annotationsBlock = annotations.javaCode() + "\n";
        }

        return "%s%s %s %s(%s) {\n\t%s\n}".formatted(
            annotationsBlock,
            modifiers.javaCode(),
            returnType.javaCode(),
            name,
            arguments.javaCode(),
            code.replace("\n", "\n\t")
        );
    }

}

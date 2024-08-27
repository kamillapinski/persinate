package dev.lapinski.persinate.generate.model;

public record JavaProperty(String name, JavaQualifiedName returnType, ClassFieldModifiers modifiers, JavaAnnotations annotations, JavaValue defaultValue) implements JavaClassField {
    @Override
    public String javaCode() {
        String defaultBlock = switch (defaultValue) {
            case null -> "";
            default -> " = " + defaultValue.javaCode();
        };

        String annotationsBlock = "";
        if (!annotations.annotations().isEmpty()) {
            annotationsBlock = annotations.javaCode() + "\n";
        }

        return annotationsBlock +  modifiers.javaCode() + " " + returnType.javaCode() + " " + name + defaultBlock + ";";
    }
}

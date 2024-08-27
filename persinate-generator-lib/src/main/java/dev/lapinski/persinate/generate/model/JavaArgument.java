package dev.lapinski.persinate.generate.model;

public record JavaArgument(String name, JavaQualifiedName type) implements JavaCode {
    @Override
    public String javaCode() {
        return type.javaCode() + " " + name;
    }
}

package dev.lapinski.persinate.generate.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record JavaClassFields(Collection<JavaClassField> fields) implements JavaCode {
    @Override
    public String javaCode() {
        return fields.stream().map(JavaClassField::javaCode).collect(Collectors.joining("\n\n"));
    }

    public static JavaClassFields of(JavaClassField... fields) {
        return new JavaClassFields(List.of(fields));
    }
}

package dev.lapinski.persinate.generate.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record JavaImplements(Collection<JavaQualifiedName> implementedInterfaces) implements JavaCode {
    @Override
    public String javaCode() {
       if (implementedInterfaces.isEmpty()) return "";


       return "implements " + implementedInterfaces.stream().map(JavaQualifiedName::javaCode).collect(Collectors.joining(", "));
    }

    public static final JavaImplements EMPTY = new JavaImplements(Collections.emptySet());

    public static JavaImplements of(JavaQualifiedName... interfaces) {
        return new JavaImplements(Set.of(interfaces));
    }

    public static JavaImplements of(String... interfaces) {
        return new JavaImplements(
            Stream.of(interfaces).map(JavaQualifiedName::fromQualifiedName).collect(Collectors.toSet())
        );
    }
}

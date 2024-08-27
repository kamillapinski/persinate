package dev.lapinski.persinate.generate.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaAnnotations(Collection<JavaAnnotation> annotations) implements JavaCode {
    @Override
    public String javaCode() {
        return annotations.stream().map(JavaAnnotation::javaCode).collect(Collectors.joining("\n"));
    }

    public static JavaAnnotations of(JavaAnnotation... annotations) {
        return new JavaAnnotations(Set.of(annotations));
    }

    public static final JavaAnnotations EMPTY = new JavaAnnotations(Collections.emptySet());
    public static final JavaAnnotations OVERRIDE = new JavaAnnotations(Set.of(
        new JavaAnnotation(
            JavaQualifiedName.fromQualifiedName("Override"),
            Collections.emptyMap()
        ))
    );
}

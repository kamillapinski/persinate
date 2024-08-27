package dev.lapinski.persinate.generate.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaImports(Collection<String> imports) implements JavaCode {
    @Override
    public String javaCode() {
        return imports.stream()
            .distinct()
            .map(x -> "import " + x + ";")
            .collect(Collectors.joining("\n"));
    }

    public static JavaImports of(String... imports) {
        return new JavaImports(Set.of(imports));
    }

    public static final JavaImports EMPTY = of();
}

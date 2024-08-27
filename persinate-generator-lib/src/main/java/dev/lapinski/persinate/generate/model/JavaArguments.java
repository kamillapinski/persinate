package dev.lapinski.persinate.generate.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record JavaArguments(Collection<JavaArgument> arguments) implements JavaCode {
    public static final JavaArguments EMPTY = new JavaArguments(Collections.emptySet());

    @Override
    public String javaCode() {
        return arguments.stream().distinct().map(JavaArgument::javaCode).collect(Collectors.joining(", "));
    }

    public static JavaArguments of(JavaArgument... arguments) {
        return new JavaArguments(List.of(arguments));
    }
}

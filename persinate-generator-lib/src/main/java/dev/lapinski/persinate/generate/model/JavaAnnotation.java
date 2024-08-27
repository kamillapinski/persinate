package dev.lapinski.persinate.generate.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public record JavaAnnotation(JavaQualifiedName annotationClass, Map<String, JavaValue> values) implements JavaValue {

    @Override
    public String javaCode() {
        String argsString;

        if (values.size() == 1 && values.containsKey("value")) {
            argsString = values.values().stream().findFirst().map(JavaValue::javaCode).get();
        } else {
            argsString = values.entrySet()
                .stream()
                .map(v -> v.getKey() + " = " + v.getValue().javaCode())
                .collect(Collectors.joining(", "));
        }
        String parenthesis = "";
        if (!argsString.isBlank()) {
            parenthesis = "(" + argsString + ")";
        }

        return "@%s%s".formatted(annotationClass.javaCode(), parenthesis);
    }

    public static JavaAnnotation jpaColumn(String columnName, boolean nullable, int length) {
        var entries = new HashSet<Map.Entry<String, JavaValue>>();

        entries.add(Map.entry("name", new JavaString(columnName)));
        entries.add(Map.entry("nullable", JavaLiteral.ofBoolean(nullable)));
        if (length > 0) {
            entries.add(Map.entry("length", JavaLiteral.ofInteger(length)));
        }

        return new JavaAnnotation(
            JavaQualifiedName.fromClass(Column.class),
            Map.ofEntries(entries.toArray(new Map.Entry[0]))
        );
    }

    public static JavaAnnotation jpaJoinColumn(String joinColumnName, boolean nullable) {
        return new JavaAnnotation(
            JavaQualifiedName.fromClass(JoinColumn.class),
            Map.of(
                "name", new JavaString(joinColumnName),
                "nullable", JavaLiteral.ofBoolean(nullable)
            )
        );
    }
    public static JavaAnnotation jpaManyToOne(boolean optional) {
        return new JavaAnnotation(
            JavaQualifiedName.fromClass(ManyToOne.class),
            Map.of("optional", JavaLiteral.ofBoolean(optional))
        );
    }
}

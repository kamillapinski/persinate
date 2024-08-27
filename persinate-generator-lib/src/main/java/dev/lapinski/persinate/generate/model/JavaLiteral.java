package dev.lapinski.persinate.generate.model;

public record JavaLiteral(String javaCode) implements JavaValue {
    public static JavaLiteral NULL = new JavaLiteral("null");

    public static JavaLiteral ofBoolean(Boolean b) {
        if (b == null) {
            return NULL;
        }

        return new JavaLiteral(b.toString());
    }

    public static JavaLiteral ofInteger(Integer i) {
        if (i == null) {
            return NULL;
        }

        return new JavaLiteral(i.toString());
    }
}

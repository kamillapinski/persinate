package dev.lapinski.persinate.generate.model;

public record JavaString(String value) implements JavaValue {
    private static String escape(String str) {
        return str.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    @Override
    public String javaCode() {
        return '"' + escape(value) + '"';
    }
}

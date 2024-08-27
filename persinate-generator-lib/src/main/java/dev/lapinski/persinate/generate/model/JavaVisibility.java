package dev.lapinski.persinate.generate.model;

public enum JavaVisibility implements JavaCode {
    PUBLIC("public"),
    PRIVATE("private"),
    PROTECTED("protected"),
    INTERNAL("");

    private final String javaVisibility;

    JavaVisibility(String javaVisibility) {
        this.javaVisibility = javaVisibility;
    }

    @Override
    public String javaCode() {
        return javaVisibility;
    }
}

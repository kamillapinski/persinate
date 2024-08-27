package dev.lapinski.persinate.generate.model;

public enum JavaStaticType implements JavaCode {
    STATIC("static"),
    NON_STATIC("");

    private final String javaStatic;

    JavaStaticType(String javaStatic) {
        this.javaStatic = javaStatic;
    }

    @Override
    public String javaCode() {
        return javaStatic;
    }
}

package dev.lapinski.persinate.generate.model;

public enum JavaFinalType implements JavaCode {
    FINAL("final"),
    NON_FINAL("");

    private final String javaFinal;

    JavaFinalType(String javaFinal) {
        this.javaFinal = javaFinal;
    }

    @Override
    public String javaCode() {
        return javaFinal;
    }
}

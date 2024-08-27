package dev.lapinski.persinate.generate.model;

public record JavaClassValue(JavaQualifiedName className) implements JavaValue {
    @Override
    public String javaCode() {
        return className.javaCode() + ".class";
    }

    public static JavaClassValue fromClass(Class<?> klass) {
        return new JavaClassValue(JavaQualifiedName.fromClass(klass));
    }

    public static JavaClassValue fromSimpleName(String simpleName) {
        return new JavaClassValue(new JavaQualifiedName("", simpleName));
    }
}

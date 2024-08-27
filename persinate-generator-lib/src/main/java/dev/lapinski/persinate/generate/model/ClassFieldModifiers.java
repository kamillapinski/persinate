package dev.lapinski.persinate.generate.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record ClassFieldModifiers(JavaVisibility visibility, JavaStaticType staticType, JavaFinalType finalType) implements JavaCode {
    @Override
    public String javaCode() {
        return Stream.<JavaCode>of(visibility, staticType, finalType)
            .map(JavaCode::javaCode)
            .filter(x -> !x.isBlank())
            .collect(Collectors.joining(" "));
    }

    public static final ClassFieldModifiers DEFAULT = new ClassFieldModifiers(JavaVisibility.INTERNAL, JavaStaticType.NON_STATIC, JavaFinalType.NON_FINAL);

    public static final ClassFieldModifiers PUBLIC_DEFAULT = new ClassFieldModifiers(JavaVisibility.PUBLIC, JavaStaticType.NON_STATIC, JavaFinalType.NON_FINAL);
    public static final ClassFieldModifiers PRIVATE_DEFAULT = new ClassFieldModifiers(JavaVisibility.PRIVATE, JavaStaticType.NON_STATIC, JavaFinalType.NON_FINAL);
}

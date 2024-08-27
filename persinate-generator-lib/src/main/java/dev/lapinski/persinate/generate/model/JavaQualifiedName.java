package dev.lapinski.persinate.generate.model;

import dev.lapinski.persinate.model.PropertyType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public record JavaQualifiedName(String packageName, String simpleName) implements JavaCode {
    public static final JavaQualifiedName LOCAL_DATE_TIME = fromClass(LocalDateTime.class);
    public static final JavaQualifiedName LOCAL_DATE = fromClass(LocalDate.class);
    public static final JavaQualifiedName INTEGER = fromQualifiedName("Integer");
    public static final JavaQualifiedName BIGDECIMAL = fromClass(BigDecimal.class);
    public static final JavaQualifiedName BIGINTEGER = fromClass(BigInteger.class);
    public static final JavaQualifiedName BOOLEAN = fromQualifiedName("Boolean");
    public static final JavaQualifiedName LONG = fromQualifiedName("Long");
    public static final JavaQualifiedName CHARACTER = fromQualifiedName("Character");
    public static final JavaQualifiedName BYTE_ARRAY = fromQualifiedName("byte[]");
    public static final JavaQualifiedName EMPTY = new JavaQualifiedName("", "");
    public static final JavaQualifiedName STRING = fromQualifiedName("String");
    public static final JavaQualifiedName VOID = fromQualifiedName("void");

    public static JavaQualifiedName fromQualifiedName(String qualifiedName) {
        if (!qualifiedName.contains(".")) {
            return new JavaQualifiedName("", qualifiedName);
        }

        String[] parts = qualifiedName.trim().split("\\.");

        if (parts.length < 2) {
            throw new IllegalArgumentException(qualifiedName + " is not a valid qualified name");
        }

        String[] packageNameParts = Arrays.copyOf(parts, parts.length - 1);

        String packageName = String.join(".", packageNameParts);
        String simpleName = parts[parts.length - 1];

        return new JavaQualifiedName(packageName, simpleName);
    }

    public static JavaQualifiedName fromEnumEntry(Enum<?> entry) {
        return new JavaQualifiedName(entry.getClass().getName(), entry.name());
    }

    public static JavaQualifiedName fromClass(Class<?> klass) {
        return new JavaQualifiedName(klass.getPackageName(), klass.getSimpleName());
    }

    public static JavaQualifiedName fromPropertyType(PropertyType propertyType) {
        return switch (propertyType) {
            case STRING -> STRING;
            case DATE_TIME -> LOCAL_DATE_TIME;
            case DATE -> LOCAL_DATE;
            case INTEGER -> INTEGER;
            case DECIMAL -> BIGDECIMAL;
            case BOOLEAN -> BOOLEAN;
            case BIGINT -> BIGINTEGER;
            case LONG -> LONG;
            case CHAR -> CHARACTER;
            case BLOB -> BYTE_ARRAY;
        };
    }

    @Override
    public String javaCode() {
        if (packageName.isBlank()) {
            return simpleName;
        }

        return packageName + "." + simpleName;
    }
}

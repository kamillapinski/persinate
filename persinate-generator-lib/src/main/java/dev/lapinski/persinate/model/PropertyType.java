package dev.lapinski.persinate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

public enum PropertyType {
    STRING(String.class),
    BOOLEAN(Boolean.class),
    CHAR(Character.class),
    DATE(LocalDate.class),
    DATE_TIME(LocalDateTime.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    BIGINT(BigInteger.class),
    DECIMAL(BigDecimal.class),
    BLOB(byte[].class);

    private final Class<?> javaType;

    PropertyType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}

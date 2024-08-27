package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.PropertyType;

public final class PropertyTypeConverter {
    public static PropertyType convert(dev.lapinski.persinate.schemas.PropertyType propertyType) {
        return switch (propertyType) {
            case BLOB -> PropertyType.BLOB;
            case CHAR -> PropertyType.CHAR;
            case DATE -> PropertyType.DATE;
            case LONG -> PropertyType.LONG;
            case BIGINT -> PropertyType.BIGINT;
            case STRING -> PropertyType.STRING;
            case BOOLEAN -> PropertyType.BOOLEAN;
            case DECIMAL -> PropertyType.DECIMAL;
            case INTEGER -> PropertyType.INTEGER;
            case DATE_TIME -> PropertyType.DATE_TIME;
        };
    }
}

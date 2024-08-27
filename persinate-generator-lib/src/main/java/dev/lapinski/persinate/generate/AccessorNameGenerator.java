package dev.lapinski.persinate.generate;

public final class AccessorNameGenerator {
    public static String generateGetterName(String propertyName) {
        return "get" + firstLetterUpperCase(propertyName);
    }

    public static String generateSetterName(String propertyName) {
        return "set" + firstLetterUpperCase(propertyName);
    }

    private static String firstLetterUpperCase(String propertyName) {
        char firstLetter = Character.toUpperCase(propertyName.charAt(0));
        return firstLetter + propertyName.substring(1);
    }
}

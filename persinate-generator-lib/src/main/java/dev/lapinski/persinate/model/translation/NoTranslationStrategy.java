package dev.lapinski.persinate.model.translation;

public final class NoTranslationStrategy implements NameTranslationStrategy {
    @Override
    public String translateName(String name) {
        return name;
    }
}

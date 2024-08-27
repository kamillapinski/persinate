package dev.lapinski.persinate.model.translation;

public interface NameTranslationStrategy {
    String translateName(String name);

    NoTranslationStrategy NONE = new NoTranslationStrategy();
    UnderscoreTranslationStrategy UNDERSCORE = new UnderscoreTranslationStrategy();
}

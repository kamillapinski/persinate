package dev.lapinski.persinate.generate;


import dev.lapinski.persinate.model.translation.NameTranslationStrategy;

public final class PersinateDefaults {
    private PersinateDefaults() {}

    public static final class Module {
        private Module() {}

        public static final String HST_ENTITY_SUFFIX = "Hst";
        public static final String HST_TABLE_SUFFIX = "_hst";
        public static final NameTranslationStrategy TABLE_TRANSLATION_STRATEGY = NameTranslationStrategy.UNDERSCORE;
        public static final NameTranslationStrategy COLUMN_TRANSLATION_STRATEGY = NameTranslationStrategy.UNDERSCORE;
    }
}

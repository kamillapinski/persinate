package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.model.Entity;

public final class HstGeneratorNameGenerator {
    public static String generateHstGeneratorName(ValuesExtractor extractor) {
        return extractor.hstEntityName() + "Generator";
    }
}

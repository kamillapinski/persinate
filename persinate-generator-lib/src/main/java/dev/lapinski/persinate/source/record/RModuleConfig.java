package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.ModuleConfig;
import dev.lapinski.persinate.model.translation.NameTranslationStrategy;

import java.util.Optional;

public record RModuleConfig(Optional<String> hstEntitySuffix, Optional<String> hstTableSuffix,
                            Optional<NameTranslationStrategy> tableNameTranslationStrategy,
                            Optional<NameTranslationStrategy> columnNameTranslationStrategy) implements ModuleConfig {
}

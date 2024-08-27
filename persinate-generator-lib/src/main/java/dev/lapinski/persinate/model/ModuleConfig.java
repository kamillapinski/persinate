package dev.lapinski.persinate.model;

import dev.lapinski.persinate.model.translation.NameTranslationStrategy;

import java.util.Optional;

public interface ModuleConfig {
    Optional<String> hstEntitySuffix();
    Optional<String> hstTableSuffix();
    Optional<NameTranslationStrategy> tableNameTranslationStrategy();
    Optional<NameTranslationStrategy> columnNameTranslationStrategy();
}

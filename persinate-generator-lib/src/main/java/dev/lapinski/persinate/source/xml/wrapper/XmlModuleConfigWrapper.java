package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.translation.NameTranslationStrategy;
import dev.lapinski.persinate.model.ModuleConfig;
import dev.lapinski.persinate.schemas.TranslationStrategy;

import java.util.Optional;

public class XmlModuleConfigWrapper implements ModuleConfig {
    private final dev.lapinski.persinate.schemas.ModuleConfig xmlModuleConfig;

    public XmlModuleConfigWrapper(dev.lapinski.persinate.schemas.ModuleConfig xmlModuleConfig) {
        this.xmlModuleConfig = xmlModuleConfig;
    }

    @Override
    public Optional<String> hstEntitySuffix() {
        return Optional.ofNullable(xmlModuleConfig.getHstEntitySuffix());
    }

    @Override
    public Optional<String> hstTableSuffix() {
        return Optional.ofNullable(xmlModuleConfig.getHstEntitySuffix());
    }

    @Override
    public Optional<NameTranslationStrategy> tableNameTranslationStrategy() {
        return Optional.ofNullable(xmlModuleConfig.getTableNameTranslation()).map(this::convertStrategy);
    }

    @Override
    public Optional<NameTranslationStrategy> columnNameTranslationStrategy() {
        return Optional.ofNullable(xmlModuleConfig.getColumnNameTranslation()).map(this::convertStrategy);
    }

    private NameTranslationStrategy convertStrategy(TranslationStrategy strategy) {
        return switch (strategy) {
            case NONE -> NameTranslationStrategy.NONE;
            case UNDERSCORE -> NameTranslationStrategy.UNDERSCORE;
        };
    }
}

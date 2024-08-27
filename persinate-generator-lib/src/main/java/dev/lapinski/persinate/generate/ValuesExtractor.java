package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.model.*;
import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.model.translation.NameTranslationStrategy;

public final class ValuesExtractor {
    private final Module module;
    private final Entity entity;
    private final EntityGenerationType generationType;

    public ValuesExtractor(Module module, Entity entity, EntityGenerationType generationType) {
        this.module = module;
        this.entity = entity;
        this.generationType = generationType;
    }

    public String tableName() {
        return entity.tableName().orElseGet(() -> getTableTranslation().translateName(entity.entityName()));
    }

    public String entityName() {
        return entity.entityName();
    }

    public String hstEntityName() {
        return entity.hstTableName().orElseGet(() -> entityName() + getHstSuffix());
    }

    private String getHstSuffix() {
        return module.moduleConfig().flatMap(ModuleConfig::hstEntitySuffix).orElse(PersinateDefaults.Module.HST_ENTITY_SUFFIX);
    }

    private String getHstTableSuffix() {
        return module.moduleConfig().flatMap(ModuleConfig::hstTableSuffix).orElse(PersinateDefaults.Module.HST_TABLE_SUFFIX);
    }

    private NameTranslationStrategy getTableTranslation() {
        return module.moduleConfig().flatMap(ModuleConfig::tableNameTranslationStrategy).orElse(PersinateDefaults.Module.TABLE_TRANSLATION_STRATEGY);
    }

    private NameTranslationStrategy getColumnTranslation() {
        return module.moduleConfig().flatMap(ModuleConfig::columnNameTranslationStrategy).orElse(PersinateDefaults.Module.COLUMN_TRANSLATION_STRATEGY);
    }



    public String hstTableName() {
        return entity.hstTableName().orElseGet(() -> tableName() + getHstTableSuffix());
    }

    public boolean replaceClassHeader() {
        return entity.nativeBlocks()
            .stream()
            .filter(n -> generationType.includesIn(n.inclusion()))
            .flatMap(n -> n.classHeader().stream())
            .anyMatch(NativeHeader::replace);
    }

    public String columnName(Property property) {
        return property.columnName().orElseGet(() -> getColumnTranslation().translateName(property.name()));
    }

    public String columnName(Reference reference) {
        return reference.columnName().orElseGet(() -> getColumnTranslation().translateName(reference.name() + "Uuid"));
    }
}

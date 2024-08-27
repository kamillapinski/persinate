package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.generate.model.JavaClass;
import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Module;

public class PersinateClassesGenerator {
    private final ModuleClassGenerator moduleClassGenerator;
    private final EntityClassGenerator entityClassGenerator;
    private final HstConverterGenerator hstConverterGenerator;

    public static final PersinateClassesGenerator INSTANCE = new PersinateClassesGenerator(
        ModuleClassGenerator.INSTANCE,
        EntityClassGenerator.INSTANCE,
        HstConverterGenerator.INSTANCE
    );

    public PersinateClassesGenerator(
        ModuleClassGenerator moduleClassGenerator,
        EntityClassGenerator entityClassGenerator,
        HstConverterGenerator hstConverterGenerator
    ) {
        this.moduleClassGenerator = moduleClassGenerator;
        this.entityClassGenerator = entityClassGenerator;
        this.hstConverterGenerator = hstConverterGenerator;
    }

    public JavaClass generateModuleClass(Module module) {
        return moduleClassGenerator.generateModuleDefinition(module);
    }

    public EntityGenerationResult generateEntity(Module module, Entity entity) {
        JavaClass liveEntityClass = entityClassGenerator.generateEntityClass(EntityGenerationType.LIVE, module, entity);

        if (entity.generateHst()) {
            return new EntityGenerationResult(
                liveEntityClass,
                entityClassGenerator.generateEntityClass(EntityGenerationType.HST, module, entity),
                hstConverterGenerator.generateHstGenerator(module, entity)
            );
        } else {
            return new EntityGenerationResult(
                liveEntityClass,
                null,
                null
            );
        }
    }
}

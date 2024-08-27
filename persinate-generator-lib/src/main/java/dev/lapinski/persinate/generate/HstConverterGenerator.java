package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.api.HstEntityGenerator;
import dev.lapinski.persinate.api.Operation;
import dev.lapinski.persinate.generate.model.*;
import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Module;

import java.util.stream.Stream;

import static dev.lapinski.persinate.generate.AccessorNameGenerator.generateGetterName;
import static dev.lapinski.persinate.generate.AccessorNameGenerator.generateSetterName;
import static dev.lapinski.persinate.generate.HstGeneratorNameGenerator.generateHstGeneratorName;

public final class HstConverterGenerator {
    public static final HstConverterGenerator INSTANCE = new HstConverterGenerator();

    public JavaClass generateHstGenerator(Module module, Entity entity) {
        var valueExtractor = new ValuesExtractor(module, entity, EntityGenerationType.HST);

        StringBuilder codeBuilder = new StringBuilder();

        codeBuilder.append("%s hstEntity = new %s();\n".formatted(valueExtractor.hstEntityName(), valueExtractor.hstEntityName()));

        appendAuditColumnsLogic(codeBuilder);
        setEntityPropertiesAndReferences(entity, codeBuilder);

        codeBuilder
            .append('\n')
            .append(
                entity.nativeConversionBlock().replaceAll("^\\s+", "")
            )
            .append('\n');

        codeBuilder.append("return hstEntity;");

        var implementedInterfaces = createImplementsStatement(valueExtractor);

        return new JavaClass(
            new JavaQualifiedName(module.targetPackageName(), generateHstGeneratorName(valueExtractor)),
            JavaImports.EMPTY,
            JavaFinalType.FINAL,
            JavaAnnotations.EMPTY,
            JavaRawBlock.EMPTY,
            JavaExtends.EMPTY,
            implementedInterfaces,
            JavaClassFields.of(
                createOverridenGenerateMethod(module, valueExtractor, codeBuilder)
            )
        );
    }

    private static JavaMethod createOverridenGenerateMethod(Module module, ValuesExtractor valueExtractor, StringBuilder codeBuilder) {
        return new JavaMethod(
            "generateHstEntity",
            new JavaQualifiedName(module.targetPackageName(), valueExtractor.hstEntityName()),
            ClassFieldModifiers.PUBLIC_DEFAULT,
            JavaAnnotations.OVERRIDE,
            JavaArguments.of(
                new JavaArgument("liveEntity", new JavaQualifiedName(module.targetPackageName(), valueExtractor.entityName())),
                new JavaArgument("operation", JavaQualifiedName.fromClass(Operation.class)),
                new JavaArgument("now", JavaQualifiedName.LOCAL_DATE_TIME)
            ),
            codeBuilder.toString()
        );
    }

    private static JavaImplements createImplementsStatement(ValuesExtractor valueExtractor) {
        return JavaImplements.of(
            new JavaQualifiedName(
                HstEntityGenerator.class.getPackageName(),
                "%s<%s, %s>".formatted(HstEntityGenerator.class.getSimpleName(), valueExtractor.entityName(), valueExtractor.hstEntityName())
            )
        );
    }

    private static void setEntityPropertiesAndReferences(Entity entity, StringBuilder codeBuilder) {
        String operationDelete = JavaQualifiedName.fromEnumEntry(Operation.DELETE).javaCode();

        codeBuilder.append("if (operation != %s) {\n".formatted(operationDelete));
        Stream.concat(
            entity.properties().stream(),
            entity.references().stream()
        ).forEach(entry -> {
            codeBuilder
                .append("\thstEntity.")
                .append(generateSetterName(entry.name()))
                .append("(")
                .append("liveEntity.")
                .append(generateGetterName(entry.name())).append("()")
                .append(");\n");
        });
        codeBuilder.append("}\n");
    }

    private static void appendAuditColumnsLogic(StringBuilder codeBuilder) {
        codeBuilder.append("hstEntity.setLiveEntity(liveEntity);\n");
        codeBuilder.append("hstEntity.setUpdatedAt(liveEntity.getUpdatedAt());\n");
        codeBuilder.append("hstEntity.setCreatedAt(liveEntity.getCreatedAt());\n");
        codeBuilder.append("hstEntity.setOperation(operation);\n");
        codeBuilder.append("hstEntity.setTransactionTimestamp(now);\n\n");
    }
}

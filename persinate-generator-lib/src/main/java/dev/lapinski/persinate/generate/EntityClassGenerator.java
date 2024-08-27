package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.api.*;
import dev.lapinski.persinate.generate.model.*;
import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.model.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.lapinski.persinate.generate.AccessorNameGenerator.generateGetterName;
import static dev.lapinski.persinate.generate.AccessorNameGenerator.generateSetterName;
import static dev.lapinski.persinate.generate.HstGeneratorNameGenerator.generateHstGeneratorName;

public class EntityClassGenerator {
    public static final EntityClassGenerator INSTANCE = new EntityClassGenerator();

    private static final Set<String> COMMON_IMPORTS = Set.of();
    private static final Set<JavaAnnotation> COMMON_ANNOTATIONS = Set.of(
        new JavaAnnotation(
            JavaQualifiedName.fromClass(jakarta.persistence.Entity.class),
            Collections.emptyMap()
        )
    );

    @SafeVarargs
    private static <T, C extends Collection<T>> C sum(Supplier<C> constructor, Collection<T>... collections) {
        var result = constructor.get();
        for (Collection<T> collection : collections) {
            result.addAll(collection);
        }
        return result;
    }

    public JavaClass generateEntityClass(EntityGenerationType generationType, Module module, Entity entity) {
        var nativeImports = getNativeImports(generationType, entity);
        var valuesExtractor = new ValuesExtractor(module, entity, generationType);

        var name = switch (generationType) {
            case LIVE -> entity.entityName();
            case HST -> valuesExtractor.hstEntityName();
        };

        return new JavaClass(
            new JavaQualifiedName(module.targetPackageName(), name),
            new JavaImports(sum(HashSet::new, COMMON_IMPORTS, nativeImports)),
            JavaFinalType.NON_FINAL,
            getClassAnnotations(generationType, entity, valuesExtractor),
            getClassNativeBlock(generationType, entity),
            getExtends(generationType, valuesExtractor),
            getImplementedInterfaces(generationType, entity),
            getClassFields(generationType, entity, valuesExtractor)
        );
    }

    private JavaClassFields getClassFields(EntityGenerationType generationType, Entity entity, ValuesExtractor valuesExtractor) {
        var result = new ArrayList<JavaClassField>();

        result.addAll(getPropertiesFields(generationType, entity, valuesExtractor));
        result.addAll(getReferencesFields(entity, valuesExtractor));
        result.addAll(getNativeBlocks(generationType, entity));

        result.addAll(getPropertiesAccessors(entity));
        result.addAll(getReferencesAccessors(entity));

        return new JavaClassFields(result);
    }

    private Collection<? extends JavaClassField> getReferencesAccessors(Entity entity) {
        return entity.references().stream().flatMap(reference -> {
            JavaQualifiedName typeQualifiedName = new JavaQualifiedName("", reference.target());

            var getter = new JavaMethod(
                generateGetterName(reference.name()),
                typeQualifiedName,
                ClassFieldModifiers.PUBLIC_DEFAULT,
                JavaAnnotations.EMPTY,
                JavaArguments.EMPTY,
                "return " + reference.name() + ";"
            );
            var setter = new JavaMethod(
                generateSetterName(reference.name()),
                JavaQualifiedName.VOID,
                ClassFieldModifiers.PUBLIC_DEFAULT,
                JavaAnnotations.EMPTY,
                JavaArguments.of(new JavaArgument("value", typeQualifiedName)),
                "this.%s = value;".formatted(reference.name())
            );

            return Stream.of(getter, setter);
        }).toList();
    }

    private Collection<? extends JavaClassField> getPropertiesAccessors(Entity entity) {
        return entity.properties().stream().flatMap(property -> {
            JavaQualifiedName typeQualifiedName = JavaQualifiedName.fromPropertyType(property.type());

            var getter = new JavaMethod(
                generateGetterName(property.name()),
                typeQualifiedName,
                ClassFieldModifiers.PUBLIC_DEFAULT,
                JavaAnnotations.EMPTY,
                JavaArguments.EMPTY,
                "return " + property.name() + ";"
            );
            var setter = new JavaMethod(
                generateSetterName(property.name()),
                JavaQualifiedName.VOID,
                ClassFieldModifiers.PUBLIC_DEFAULT,
                JavaAnnotations.EMPTY,
                JavaArguments.of(new JavaArgument("value", typeQualifiedName)),
                "this.%s = value;".formatted(property.name())
            );

            return Stream.of(getter, setter);
        }).toList();
    }

    private Collection<JavaClassField> getPropertiesFields(EntityGenerationType generationType, Entity entity, ValuesExtractor valuesExtractor) {
        return entity.properties().stream().flatMap(p -> getFieldsForProperty(generationType, p, valuesExtractor)).toList();
    }

    private Stream<JavaClassField> getFieldsForProperty(EntityGenerationType generationType, Property property, ValuesExtractor valuesExtractor) {
        String nativeHeader = property.nativeHeader().map(NativeHeader::codeBlock).map(String::trim).orElse("");

        boolean optional = property.optional() || generationType == EntityGenerationType.HST;

        JavaQualifiedName typeQualifiedName = JavaQualifiedName.fromPropertyType(property.type());

        if (nativeHeader.isEmpty()) {
            int columnLength = switch (generationType) {
                case LIVE -> property.length();
                case HST -> 0;
            };

            var columnAnnotation = JavaAnnotation.jpaColumn(valuesExtractor.columnName(property), optional, columnLength);
            JavaAnnotations fieldAnnotations = JavaAnnotations.of(columnAnnotation);

            return Stream.of(
                new JavaProperty(
                    property.name(),
                    typeQualifiedName,
                    ClassFieldModifiers.PRIVATE_DEFAULT,
                    fieldAnnotations,
                    null
                )
            );
        } else {
            return Stream.of(
                new JavaRawBlock(nativeHeader),
                new JavaProperty(
                    property.name(),
                    typeQualifiedName,
                    ClassFieldModifiers.PRIVATE_DEFAULT,
                    JavaAnnotations.EMPTY,
                    null
                )
            );
        }

    }

    private Collection<JavaClassField> getReferencesFields(Entity entity, ValuesExtractor valuesExtractor) {
        return entity.references().stream().flatMap(reference -> getFieldsForReference(reference, valuesExtractor)).toList();
    }

    private Stream<JavaClassField> getFieldsForReference(Reference reference, ValuesExtractor valuesExtractor) {
        String nativeHeader = reference.nativeHeader().map(NativeHeader::codeBlock).map(String::trim).orElse("");

        JavaQualifiedName typeQualifiedName = new JavaQualifiedName("", reference.target());

        if (nativeHeader.isEmpty()) {
            boolean optional = reference.optional();

            var columnAnnotation = JavaAnnotation.jpaJoinColumn(valuesExtractor.columnName(reference), optional);
            JavaAnnotations fieldAnnotations = JavaAnnotations.of(
                JavaAnnotation.jpaManyToOne(optional),
                columnAnnotation
            );

            return Stream.of(
                new JavaProperty(
                    reference.name(),
                    typeQualifiedName,
                    ClassFieldModifiers.PRIVATE_DEFAULT,
                    fieldAnnotations,
                    null
                )
            );
        } else {
            return Stream.of(
                new JavaRawBlock(nativeHeader),
                new JavaProperty(
                    reference.name(),
                    typeQualifiedName,
                    ClassFieldModifiers.PRIVATE_DEFAULT,
                    JavaAnnotations.EMPTY,
                    null
                )
            );
        }

    }

    private Collection<? extends JavaClassField> getNativeBlocks(EntityGenerationType generationType, Entity entity) {
        return entity.nativeBlocks()
            .stream()
            .filter(n -> generationType.includesIn(n.inclusion()))
            .flatMap(n -> n.codeBlock().stream())
            .map(JavaRawBlock::new)
            .toList();
    }

    private JavaImplements getImplementedInterfaces(EntityGenerationType generationType, Entity entity) {
        var interfaces = entity.nativeBlocks()
            .stream()
            .filter(n -> generationType.includesIn(n.inclusion()))
            .flatMap(n -> n.implementedInterfaces().stream())
            .map(JavaQualifiedName::fromQualifiedName)
            .collect(Collectors.toSet());

        return new JavaImplements(interfaces);
    }

    private JavaExtends getExtends(EntityGenerationType generationType, ValuesExtractor valuesExtractor) {
        return switch (generationType) {
            case LIVE -> JavaExtends.fromClass(PersinateEntity.class);
            case HST -> {
                String simpleName = "%s<%s>".formatted(PersinateHstEntity.class.getSimpleName(), valuesExtractor.entityName());
                yield new JavaExtends(new JavaQualifiedName(PersinateHstEntity.class.getPackageName(), simpleName));
            }
        };
    }

    private JavaRawBlock getClassNativeBlock(EntityGenerationType generationType, Entity entity) {
        String code = entity.nativeBlocks()
            .stream()
            .filter(b -> generationType.includesIn(b.inclusion()))
            .flatMap(n -> n.classHeader().stream())
            .map(NativeHeader::codeBlock)
            .collect(Collectors.joining("\n"));

        return new JavaRawBlock(code);
    }

    private static Set<String> getNativeImports(EntityGenerationType generationType, Entity entity) {
        return entity.nativeBlocks()
            .stream()
            .filter(block -> generationType.includesIn(block.inclusion()))
            .flatMap(e -> e.imports().stream())
            .collect(Collectors.toSet());
    }

    private static JavaAnnotations getClassAnnotations(EntityGenerationType generationType, Entity entity, ValuesExtractor valuesExtractor) {
        if (valuesExtractor.replaceClassHeader()) {
            return JavaAnnotations.EMPTY;
        }

        if (generationType == EntityGenerationType.LIVE) {
            var liveAnnotations = Set.of(
                new JavaAnnotation(
                    JavaQualifiedName.fromClass(jakarta.persistence.Table.class),
                    Map.of("name", new JavaString(valuesExtractor.tableName()))
                ),
                new JavaAnnotation(
                    JavaQualifiedName.fromClass(LiveFor.class),
                    Map.of("value", JavaClassValue.fromSimpleName(valuesExtractor.hstEntityName()))
                )
            );

            Set<JavaAnnotation> hstGeneratorAnnotations = Collections.emptySet();
            if (entity.generateHst()) {
                hstGeneratorAnnotations = Set.of(new JavaAnnotation(
                    JavaQualifiedName.fromClass(HstGenerator.class),
                    Map.of("value", JavaClassValue.fromSimpleName(generateHstGeneratorName(valuesExtractor)))
                ));
            }

            return new JavaAnnotations(sum(HashSet::new, COMMON_ANNOTATIONS, liveAnnotations, hstGeneratorAnnotations));
        } else {
            var hstAnnotations = Set.of(
                new JavaAnnotation(
                    JavaQualifiedName.fromClass(jakarta.persistence.Table.class),
                    Map.of("name", new JavaString(valuesExtractor.hstTableName()))
                ),
                new JavaAnnotation(
                    JavaQualifiedName.fromClass(HstFor.class),
                    Map.of("value", JavaClassValue.fromSimpleName(valuesExtractor.entityName()))
                )
            );

            return new JavaAnnotations(sum(HashSet::new, COMMON_ANNOTATIONS, hstAnnotations));
        }
    }


}

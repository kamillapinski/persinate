package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.api.PersinateModule;
import dev.lapinski.persinate.generate.model.*;
import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Module;

import java.util.Set;
import java.util.stream.Collectors;

public class ModuleClassGenerator {
    public static final ModuleClassGenerator INSTANCE = new ModuleClassGenerator();

    public JavaClass generateModuleDefinition(Module module) {
        String entitiesList = module.entities()
            .stream()
            .map(Entity::entityName).map(name -> name + ".class")
            .collect(Collectors.joining(",\n"))
            .replace("\n", "\n\t");

        return new JavaClass(
            new JavaQualifiedName(module.targetPackageName(), module.name() + "Module"),
            JavaImports.of(
                PersinateModule.class.getPackageName() + ".*",
                Set.class.getName()
            ),
            JavaFinalType.FINAL,
            JavaAnnotations.EMPTY,
            JavaRawBlock.EMPTY,
            JavaExtends.EMPTY,
            JavaImplements.of(PersinateModule.class.getSimpleName()),
            JavaClassFields.of(
                overriddenMethod(
                    "name",
                    JavaQualifiedName.STRING,
                    "return " + new JavaString(module.name()).javaCode() + ";"
                ),
                overriddenMethod(
                    "entitiesPackage",
                    JavaQualifiedName.fromQualifiedName("Package"),
                    "return getClass().getPackage();"
                ),
                overriddenMethod(
                    "entityClasses",
                    JavaQualifiedName.fromQualifiedName("Set<Class<? extends PersinateEntity>>"),
                    "return Set.of(\n\t%s\n);".formatted(entitiesList)
                ),
                overriddenMethod(
                    "classLoader",
                    JavaQualifiedName.fromQualifiedName("ClassLoader"),
                    "return getClass().getClassLoader();"
                )
            )
        );
    }

    private static JavaMethod overriddenMethod(String name, JavaQualifiedName returnType, String code) {
        return new JavaMethod(
            name,
            returnType,
            ClassFieldModifiers.PUBLIC_DEFAULT,
            JavaAnnotations.OVERRIDE,
            JavaArguments.EMPTY,
            code
        );
    }
}

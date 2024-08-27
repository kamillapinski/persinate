package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.generate.model.JavaClass;
import dev.lapinski.persinate.source.ModuleSource;
import dev.lapinski.persinate.source.xml.PersinateJaxbContext;
import dev.lapinski.persinate.source.xml.XmlModuleSource;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

public class PersinateGenerator {
    private final PersinateClassesGenerator persinateClassesGenerator;
    private final PersinateJaxbContext persinateJaxbContext;

    public PersinateGenerator(PersinateClassesGenerator persinateClassesGenerator, PersinateJaxbContext persinateJaxbContext) {
        this.persinateClassesGenerator = Objects.requireNonNull(persinateClassesGenerator);
        this.persinateJaxbContext = Objects.requireNonNull(persinateJaxbContext);
    }

    public void generateModule(Collection<File> modulesFiles, Path targetSourceDirectory) {
        ModuleSource moduleSource = new XmlModuleSource(modulesFiles, persinateJaxbContext);

        moduleSource.findModules().forEach(module -> {
            Path targetPackageDirectory = targetSourceDirectory.resolve(module.targetPackageName().replace('.', '/'));
            try {
                Files.createDirectories(targetPackageDirectory);
            } catch (IOException e) {
                throw new UncheckedIOException("Could not create directories " + targetPackageDirectory, e);
            }

            module.entities().forEach(entity -> {
                var entityResult = persinateClassesGenerator.generateEntity(module, entity);

                JavaClass liveClass = entityResult.liveEntityClass();
                writeJavaClass(targetPackageDirectory, liveClass);

                entityResult.hstEntityClass().ifPresent(hstClass -> {
                    writeJavaClass(targetPackageDirectory, hstClass);
                });
                entityResult.hstGeneratorClass().ifPresent(hstGeneratorClass -> {
                    writeJavaClass(targetPackageDirectory, hstGeneratorClass);
                });
            });

            var moduleClass = persinateClassesGenerator.generateModuleClass(module);
            writeJavaClass(targetPackageDirectory, moduleClass);
        });
    }

    private void writeJavaClass(Path targetPackageDirectory, JavaClass javaClass) {
        try {
            Files.writeString(targetPackageDirectory.resolve(javaClass.qualifiedName().simpleName() + ".java"), javaClass.javaCode());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write class " + javaClass.qualifiedName().javaCode(), e);
        }
    }
}

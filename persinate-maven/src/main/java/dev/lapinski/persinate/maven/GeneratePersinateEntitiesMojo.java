package dev.lapinski.persinate.maven;

import dev.lapinski.persinate.generate.PersinateClassesGenerator;
import dev.lapinski.persinate.generate.PersinateGenerator;
import dev.lapinski.persinate.source.xml.PersinateJaxbContext;
import jakarta.xml.bind.JAXBException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Mojo(name = "persinate-entities", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GeneratePersinateEntitiesMojo extends AbstractMojo {
    @Parameter(name = "modules", required = true)
    String[] modules;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/persinate/", property = "targetDirectory", required = true)
    String targetDirectory;

    @Override
    public void execute() {
        Path targetPath = Path.of(targetDirectory);

        var moduleFiles = Set.of(modules)
            .stream()
            .map(File::new)
            .collect(Collectors.toSet());
        try {
            new PersinateGenerator(PersinateClassesGenerator.INSTANCE, PersinateJaxbContext.create()).generateModule(moduleFiles, targetPath);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}

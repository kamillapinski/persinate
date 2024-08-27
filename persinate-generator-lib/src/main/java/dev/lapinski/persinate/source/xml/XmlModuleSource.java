package dev.lapinski.persinate.source.xml;

import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.schemas.Entity;
import dev.lapinski.persinate.schemas.EntityRef;
import dev.lapinski.persinate.source.ModuleSource;
import dev.lapinski.persinate.source.xml.wrapper.XmlModuleWrapper;
import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

public class XmlModuleSource implements ModuleSource {
    private final Collection<File> files;
    private final PersinateJaxbContext jaxbContext;

    public XmlModuleSource(Collection<File> files, PersinateJaxbContext jaxbContext) {
        this.files = files;
        this.jaxbContext = jaxbContext;
    }

    @Override
    public Stream<Module> findModules() {
        return files.stream().map(moduleFile -> {
            try (var moduleReader = Files.newBufferedReader(moduleFile.toPath())) {
                return readModule(moduleFile, moduleReader);
            } catch (Exception ex) {
                throw new IllegalStateException("Failed to parse module " + moduleFile.getPath(), ex);
            }
        });
    }

    private XmlModuleWrapper readModule(File moduleFile, BufferedReader moduleReader) throws JAXBException {
        var xmlModule = jaxbContext.parseModule(moduleReader);

        var xmlEntities = xmlModule.getEntities()
            .getEntity()
            .stream()
            .map(entityRef -> readEntity(moduleFile, entityRef))
            .toList();

        return new XmlModuleWrapper(xmlModule, xmlEntities);
    }

    private Entity readEntity(File moduleFile, EntityRef entityRef) {
        Path entityFilePath = moduleFile.toPath().getParent().resolve(entityRef.getPath());

        try (var entityReader = Files.newBufferedReader(entityFilePath)) {
            return jaxbContext.parseEntity(entityReader);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to read entity " + entityRef.getPath(), ex);
        }
    }
}

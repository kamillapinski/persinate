package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.model.ModuleConfig;

import java.util.Collection;
import java.util.Optional;

public class XmlModuleWrapper implements Module {
    private final dev.lapinski.persinate.schemas.Module xmlModule;
    private final Collection<dev.lapinski.persinate.schemas.Entity> entities;

    public XmlModuleWrapper(dev.lapinski.persinate.schemas.Module xmlModule, Collection<dev.lapinski.persinate.schemas.Entity> entities) {
        this.xmlModule = xmlModule;
        this.entities = entities;
    }

    @Override
    public String targetPackageName() {
        return xmlModule.getPackage();
    }

    @Override
    public Optional<? extends ModuleConfig> moduleConfig() {
        return Optional.ofNullable(xmlModule.getConfig()).map(XmlModuleConfigWrapper::new);
    }

    @Override
    public Collection<? extends Entity> entities() {
        return entities.stream().map(XmlEntityWrapper::new).toList();
    }

    @Override
    public String name() {
        return xmlModule.getName();
    }
}

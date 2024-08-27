package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.*;
import dev.lapinski.persinate.schemas.NativeProperties;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class XmlEntityWrapper implements Entity {
    private final dev.lapinski.persinate.schemas.Entity xmlEntity;

    public XmlEntityWrapper(dev.lapinski.persinate.schemas.Entity xmlEntity) {
        this.xmlEntity = xmlEntity;
    }

    @Override
    public Collection<? extends Native> nativeBlocks() {
        return xmlEntity.getNativeOrPropertyOrReference()
            .stream()
            .filter(x -> x instanceof dev.lapinski.persinate.schemas.NativeProperties)
            .map(x -> (NativeProperties) x)
            .map(XmlNativeWrapper::new)
            .toList();
    }

    @Override
    public Collection<? extends Property> properties() {
        return xmlEntity.getNativeOrPropertyOrReference()
            .stream()
            .filter(x -> x instanceof dev.lapinski.persinate.schemas.Property)
            .map(x -> (dev.lapinski.persinate.schemas.Property) x)
            .map(XmlPropertyWrapper::new)
            .toList();
    }

    @Override
    public Collection<? extends Reference> references() {
        return xmlEntity.getNativeOrPropertyOrReference()
            .stream()
            .filter(x -> x instanceof dev.lapinski.persinate.schemas.Reference)
            .map(x -> (dev.lapinski.persinate.schemas.Reference) x)
            .map(XmlReferenceWrapper::new)
            .toList();
    }

    @Override
    public String nativeConversionBlock() {
        return xmlEntity.getNativeOrPropertyOrReference()
            .stream()
            .filter(x -> x instanceof NativeProperties)
            .map(x -> (NativeProperties) x)
            .map(NativeProperties::getConversionBlock)
            .collect(Collectors.joining("\n\n"));
    }

    @Override
    public boolean generateHst() {
        return xmlEntity.isGenerateHst();
    }


    @Override
    public String entityName() {
        return xmlEntity.getName();
    }

    @Override
    public Optional<String> tableName() {
        return Optional.ofNullable(xmlEntity.getTableName());
    }

    @Override
    public Optional<String> hstTableName() {
        return Optional.ofNullable(xmlEntity.getHstTableName());
    }
}

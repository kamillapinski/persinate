package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.Reference;

import java.util.Optional;

public class XmlReferenceWrapper implements Reference {
    private final dev.lapinski.persinate.schemas.Reference xmlReference;

    public XmlReferenceWrapper(dev.lapinski.persinate.schemas.Reference xmlReference) {
        this.xmlReference = xmlReference;
    }

    @Override
    public String name() {
        return xmlReference.getName();
    }

    @Override
    public String target() {
        return xmlReference.getTarget();
    }

    @Override
    public boolean optional() {
        return xmlReference.isOptional();
    }

    @Override
    public Optional<NativeHeader> nativeHeader() {
        return Optional.ofNullable(xmlReference.getNativeHeader()).map(XmlNativeHeaderWrapper::new);
    }

    @Override
    public Optional<String> columnName() {
        return Optional.ofNullable(xmlReference.getColumnName());
    }
}

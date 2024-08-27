package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.Property;
import dev.lapinski.persinate.model.PropertyType;

import java.util.Optional;

public class XmlPropertyWrapper implements Property {
    private final dev.lapinski.persinate.schemas.Property xmlProperty;

    public XmlPropertyWrapper(dev.lapinski.persinate.schemas.Property xmlProperty) {
        this.xmlProperty = xmlProperty;
    }

    @Override
    public String name() {
        return xmlProperty.getName();
    }

    @Override
    public PropertyType type() {
        return PropertyTypeConverter.convert(xmlProperty.getType());
    }

    @Override
    public int length() {
        return xmlProperty.getLength();
    }

    @Override
    public boolean optional() {
        return xmlProperty.isOptional();
    }

    @Override
    public Optional<NativeHeader> nativeHeader() {
        return Optional.ofNullable(xmlProperty.getNativeHeader()).map(XmlNativeHeaderWrapper::new);
    }

    @Override
    public Optional<String> columnName() {
        return Optional.ofNullable(xmlProperty.getColumnName());
    }
}

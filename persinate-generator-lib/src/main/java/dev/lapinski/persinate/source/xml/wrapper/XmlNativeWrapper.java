package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.Native;
import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.NativeInclusion;
import dev.lapinski.persinate.schemas.NativeProperties;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.emptySet;

public class XmlNativeWrapper implements Native {
    private final NativeProperties xmlNative;

    public XmlNativeWrapper(NativeProperties xmlNative) {
        this.xmlNative = xmlNative;
    }

    @Override
    public Collection<String> imports() {
        if (xmlNative.getImports() != null) {
            return xmlNative.getImports().getImport();
        }

        return emptySet();
    }

    @Override
    public Collection<String> implementedInterfaces() {
       if (xmlNative.getImplements() != null) {
           return xmlNative.getImplements().getInterface();
       }

       return emptySet();
    }

    @Override
    public Optional<String> codeBlock() {
       return Optional.ofNullable(xmlNative.getCode()).filter(x -> !x.isBlank());
    }

    @Override
    public Optional<NativeHeader> classHeader() {
        return Optional.ofNullable(xmlNative.getClassHeader()).map(XmlNativeHeaderWrapper::new);
    }

    @Override
    public NativeInclusion inclusion() {
        return InclusionConverter.convert(xmlNative.getInclude());
    }
}

package dev.lapinski.persinate.source.xml.wrapper;


import dev.lapinski.persinate.schemas.NativeHeader;

public class XmlNativeHeaderWrapper implements dev.lapinski.persinate.model.NativeHeader {
    private final NativeHeader xmlNativeHeader;

    public XmlNativeHeaderWrapper(NativeHeader xmlNativeHeader) {
        this.xmlNativeHeader = xmlNativeHeader;
    }

    @Override
    public boolean replace() {
        return xmlNativeHeader.isReplace();
    }

    @Override
    public String codeBlock() {
        return xmlNativeHeader.getValue();
    }
}

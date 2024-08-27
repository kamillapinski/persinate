package dev.lapinski.persinate.source.xml;

import dev.lapinski.persinate.schemas.Entity;
import dev.lapinski.persinate.schemas.Module;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.Reader;

public final class PersinateJaxbContext {
    private final Unmarshaller unmarshaller;

    private PersinateJaxbContext(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public static PersinateJaxbContext create() throws JAXBException {
        var context = JAXBContext.newInstance(Module.class, Entity.class);

        return new PersinateJaxbContext(
            context.createUnmarshaller()
        );
    }

    public Module parseModule(Reader reader) throws JAXBException {
        return (Module) unmarshaller.unmarshal(reader);
    }

    public Entity parseEntity(Reader reader) throws JAXBException {
        return (Entity) unmarshaller.unmarshal(reader);
    }
}

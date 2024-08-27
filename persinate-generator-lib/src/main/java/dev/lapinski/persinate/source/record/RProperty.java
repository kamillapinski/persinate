package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.Property;
import dev.lapinski.persinate.model.PropertyType;

import java.util.Optional;

public record RProperty(String name, PropertyType type, int length, boolean optional, Optional<NativeHeader> nativeHeader, Optional<String> columnName) implements Property {
}

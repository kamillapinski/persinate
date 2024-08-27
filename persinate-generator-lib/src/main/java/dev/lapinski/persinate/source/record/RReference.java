package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.Reference;

import java.util.Optional;

public record RReference(String name, String target, boolean optional, Optional<NativeHeader> nativeHeader, Optional<String> columnName) implements Reference {
}

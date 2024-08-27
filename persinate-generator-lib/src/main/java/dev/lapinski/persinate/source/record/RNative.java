package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.Native;
import dev.lapinski.persinate.model.NativeHeader;
import dev.lapinski.persinate.model.NativeInclusion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public record RNative(Collection<String> imports, Collection<String> implementedInterfaces, Optional<String> codeBlock, Optional<NativeHeader> classHeader, NativeInclusion inclusion) implements Native {
}

package dev.lapinski.persinate.model;

import java.util.Collection;
import java.util.Optional;

public interface Native {
    Collection<String> imports();
    Collection<String> implementedInterfaces();
    Optional<String> codeBlock();
    Optional<NativeHeader> classHeader();
    NativeInclusion inclusion();
}

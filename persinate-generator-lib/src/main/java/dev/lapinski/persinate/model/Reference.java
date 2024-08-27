package dev.lapinski.persinate.model;

import java.util.Optional;

public interface Reference extends EntityEntry {
    String name();
    String target();
    boolean optional();
    Optional<NativeHeader> nativeHeader();
    Optional<String> columnName();
}

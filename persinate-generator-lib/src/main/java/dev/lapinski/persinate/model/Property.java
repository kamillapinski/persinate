package dev.lapinski.persinate.model;

import java.util.Optional;

public interface Property extends EntityEntry {
    String name();
    PropertyType type();
    int length();
    boolean optional();
    Optional<NativeHeader> nativeHeader();
    Optional<String> columnName();
}

package dev.lapinski.persinate.model;

import java.util.Collection;
import java.util.Optional;

public interface Entity {
    Collection<? extends Native> nativeBlocks();
    Collection<? extends Property> properties();
    Collection<? extends Reference> references();
    String nativeConversionBlock();
    boolean generateHst();
    String entityName();
    Optional<String> tableName();
    Optional<String> hstTableName();
}

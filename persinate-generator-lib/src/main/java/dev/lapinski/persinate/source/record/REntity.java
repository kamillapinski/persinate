package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Native;
import dev.lapinski.persinate.model.Property;
import dev.lapinski.persinate.model.Reference;

import java.util.Collection;
import java.util.Optional;

public record REntity(String entityName,
                      Optional<String> tableName,
                      Optional<String> hstTableName,
                      Collection<? extends Native> nativeBlocks,
                      Collection<? extends Property> properties,
                      Collection<? extends Reference> references,
                      String nativeConversionBlock,
                      boolean generateHst
) implements Entity {
}

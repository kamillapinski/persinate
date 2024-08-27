package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.Entity;
import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.model.ModuleConfig;

import java.util.Collection;
import java.util.Optional;

public record RModule(String name,
                      String targetPackageName,
                      Collection<? extends Entity> entities,
                      Optional<? extends ModuleConfig> moduleConfig
) implements Module {
}

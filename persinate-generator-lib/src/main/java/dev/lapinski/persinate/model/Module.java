package dev.lapinski.persinate.model;

import java.util.Collection;
import java.util.Optional;

public interface Module {
    String targetPackageName();
    Optional<? extends ModuleConfig> moduleConfig();
    Collection<? extends Entity> entities();
    String name();
}

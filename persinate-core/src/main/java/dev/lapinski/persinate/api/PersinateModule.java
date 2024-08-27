package dev.lapinski.persinate.api;

import java.util.Set;

public interface PersinateModule {
    String name();
    Package entitiesPackage();
    Set<Class<? extends PersinateEntity>> entityClasses();
    ClassLoader classLoader();
}

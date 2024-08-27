package dev.lapinski.persinate.source;

import dev.lapinski.persinate.model.Module;

import java.util.stream.Stream;

public interface ModuleSource {
    Stream<Module> findModules();
}

package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.model.Module;
import dev.lapinski.persinate.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

class ModuleClassGeneratorTest {
    static ModuleClassGenerator generator = new ModuleClassGenerator();

    @Test
    void generate() {
        String code = generator.generateModuleDefinition(
            new TestModule(
                "Test",
                "com.test",
                List.of(
                    new TestEntity("TestEntity1"),
                    new TestEntity("TestEntity2")
                )
            )
        ).javaCode();

        System.out.println(code);
    }

    // -------------------
    private record TestEntity(String entityName) implements Entity {

        @Override
        public Collection<? extends Native> nativeBlocks() {
            return List.of();
        }

        @Override
        public Collection<? extends Property> properties() {
            return List.of();
        }

        @Override
        public Collection<? extends Reference> references() {
            return List.of();
        }

        @Override
        public String nativeConversionBlock() {
            return "";
        }

        @Override
        public boolean generateHst() {
            return false;
        }


        @Override
        public Optional<String> tableName() {
            return Optional.empty();
        }

        @Override
        public Optional<String> hstTableName() {
            return Optional.empty();
        }
    }

    private record TestModule(String name, String targetPackageName,
                              Collection<? extends Entity> entities) implements Module {
        @Override
        public Optional<? extends ModuleConfig> moduleConfig() {
            return Optional.empty();
        }
    }
}
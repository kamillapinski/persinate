package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.model.PropertyType;
import dev.lapinski.persinate.source.record.REntity;
import dev.lapinski.persinate.source.record.RModule;
import dev.lapinski.persinate.source.record.RProperty;
import dev.lapinski.persinate.source.record.RReference;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HstConverterGeneratorTest {
    static HstConverterGenerator hstConverterGenerator = new HstConverterGenerator();

    @Test
    void test() {
        var entity = new REntity(
            "TestEntity",
            Optional.empty(),
            Optional.empty(),
            Collections.emptySet(),
            Set.of(
                new RProperty("firstProperty", PropertyType.STRING, 0, false, Optional.empty(), Optional.empty()),
                new RProperty("secondProperty", PropertyType.INTEGER, 0, true,Optional.empty(), Optional.empty())
            ),
            Set.of(
                new RReference("firstReference", "com.db.Gugu", false, Optional.empty(), Optional.empty()),
                new RReference("secondReference", "com.db.Dudu", true, Optional.empty(), Optional.empty())
            ),
            "",
            true
        );

        var module = new RModule("Test", "com.test", Set.of(entity), Optional.empty());

        String code = hstConverterGenerator.generateHstGenerator(module, entity).javaCode();
        System.out.println(code);
    }
}
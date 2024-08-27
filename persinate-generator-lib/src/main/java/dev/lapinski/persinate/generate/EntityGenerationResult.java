package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.generate.model.JavaClass;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class EntityGenerationResult {
    private final JavaClass liveEntityClass;
    private final JavaClass hstEntityClass;
    private final JavaClass hstGeneratorClass;

    public EntityGenerationResult(JavaClass liveEntityClass, JavaClass hstEntityClass, JavaClass hstGeneratorClass) {
        this.liveEntityClass = Objects.requireNonNull(liveEntityClass, "liveEntityClass");

        if (hstEntityClass == null ^ hstGeneratorClass == null) {
            throw new IllegalArgumentException("hstEntityClass or hstGeneratorClass is null while the other is not");
        }

        this.hstEntityClass = hstEntityClass;
        this.hstGeneratorClass = hstGeneratorClass;
    }

    public JavaClass liveEntityClass() {
        return liveEntityClass;
    }

    public Optional<JavaClass> hstEntityClass() {
        return Optional.ofNullable(hstEntityClass);
    }

    public Optional<JavaClass> hstGeneratorClass() {
        return Optional.ofNullable(hstGeneratorClass);
    }

}

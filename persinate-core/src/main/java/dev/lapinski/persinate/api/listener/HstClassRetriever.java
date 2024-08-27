package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.*;

import java.util.Optional;

public final class HstClassRetriever {
    public static final HstClassRetriever INSTANCE = new HstClassRetriever();

    @SuppressWarnings("unchecked")
    public HstEntityGenerator<PersinateEntity, ? extends PersinateHstEntity<?>> getHstEntityGenerator(PersinateEntity entity) {
        var hstGeneratorAnnotation = entity.getClass().getAnnotation(HstGenerator.class);
        if (hstGeneratorAnnotation == null) {
            throw new IllegalStateException("Live entity class %s is not annotated with @HstGenerator".formatted(entity.getClass()));
        }

        Class<? extends HstEntityGenerator<?, ?>> entityHeneratorClass = hstGeneratorAnnotation.value();

        try {
            return (HstEntityGenerator<PersinateEntity, ? extends PersinateHstEntity<?>>) entityHeneratorClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("HstEntityGenerator %s could not be instantiated.".formatted(entityHeneratorClass), e);
        }
    }

    public Optional<Class<? extends PersinateHstEntity<?>>> getHstEntityClass(PersinateEntity entity) {
        if (entity instanceof PersinateHstEntity<?>) {
            throw new IllegalArgumentException("Provided entity %s is hst entity".formatted(entity.getClass()));
        }

        return Optional
            .ofNullable(
                entity.getClass().getAnnotation(LiveFor.class)
            )
            .map(LiveFor::value);
    }

}

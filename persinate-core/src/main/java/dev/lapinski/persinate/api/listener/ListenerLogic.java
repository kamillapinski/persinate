package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.Operation;
import dev.lapinski.persinate.api.PersinateEntity;
import dev.lapinski.persinate.api.PersinateHstEntity;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

public final class ListenerLogic {
    public static final ListenerLogic INSTANCE = new ListenerLogic(PersinateClock.DEFAULT, HstClassRetriever.INSTANCE);

    private final PersinateClock persinateClock;
    private final HstClassRetriever hstClassRetriever;

    public static ListenerLogic forClock(PersinateClock clock) {
        return new ListenerLogic(clock, HstClassRetriever.INSTANCE);
    }

    public ListenerLogic(PersinateClock persinateClock, HstClassRetriever hstClassRetriever) {
        this.persinateClock = Objects.requireNonNull(persinateClock, "persinateClock");
        this.hstClassRetriever = Objects.requireNonNull(hstClassRetriever, "hstClassRetriever");
    }

    public boolean isLive(Object entity) {
        return entity instanceof PersinateEntity && !(entity instanceof PersinateHstEntity<?>);
    }


    private boolean hasHstEntityClass(PersinateEntity entity) {
        return hstClassRetriever.getHstEntityClass(entity).isPresent();
    }

    public LocalDateTime getCurrentDateTime() {
        return persinateClock.now();
    }

    public <T> void generateHstEntity(Supplier<Object> entityExtractor, Session session, Operation operation) {
        var rawEntity = entityExtractor.get();

        // We do not want to process hst entities or non-Persinate stuff
        if (!isLive(rawEntity)) {
            return;
        }

        var entity = (PersinateEntity) rawEntity;

        // Do not create a hst entity when the hst entity class does not exist
        if (!hasHstEntityClass(entity)) {
            return;
        }

        var hstGenerator = hstClassRetriever.getHstEntityGenerator(entity);
        var hstEntity = hstGenerator.generateHstEntity(entity, operation, persinateClock.now());

        session.persist(hstEntity);
    }
}

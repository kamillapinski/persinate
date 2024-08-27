package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.HstEntityGenerator;
import dev.lapinski.persinate.api.Operation;
import dev.lapinski.persinate.api.PersinateEntity;
import dev.lapinski.persinate.api.PersinateHstEntity;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.PostInsertEvent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ListenerLogicTest {
    static final LocalDateTime NOW = LocalDateTime.of(2020, 1, 1, 12, 0);

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @EnumSource(Operation.class)
    void generateHstEntity(Operation operation) {
        PersinateClock persinateClock = mock(PersinateClock.class);
        HstClassRetriever hstClassRetriever = mock(HstClassRetriever.class);

        ListenerLogic listenerLogic = new ListenerLogic(persinateClock, hstClassRetriever);

        var liveEntity = mock(PersinateEntity.class);
        var hstEntity = mock(PersinateHstEntity.class);

        var postInsertEvent = mock(PostInsertEvent.class);
        var session = mock(EventSource.class);
        var hstEntityGenerator = mock(HstEntityGenerator.class);

        when(persinateClock.now()).thenReturn(NOW);

        when(postInsertEvent.getEntity()).thenReturn(liveEntity);
        when(postInsertEvent.getSession()).thenReturn(session);

        Optional<Class<? extends PersinateHstEntity<?>>> hstClass = Optional.of((Class<? extends PersinateHstEntity<?>>) hstEntity.getClass());
        when(hstClassRetriever.getHstEntityClass(liveEntity)).thenReturn(hstClass);
        when(hstClassRetriever.getHstEntityGenerator(liveEntity)).thenReturn(hstEntityGenerator);
        when(hstEntityGenerator.generateHstEntity(liveEntity, operation, NOW)).thenReturn(hstEntity);

        listenerLogic.generateHstEntity(() -> liveEntity, session, operation);

        verify(session).persist(hstEntity);
    }
}
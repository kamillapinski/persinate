package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.PersinateEntity;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.PreInsertEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersinatePreInsertListenerTest {

    static final LocalDateTime NOW = LocalDateTime.of(2020, 1, 1, 12, 0);

    @Test
    void setsDates() {
        var listenerLogic = mock(ListenerLogic.class);
        var entity = new PersinateEntity() {
        };

        when(listenerLogic.isLive(entity)).thenReturn(true);
        when(listenerLogic.getCurrentDateTime()).thenReturn(NOW);

        var listener = new PersinatePreInsertListener(listenerLogic);

        var session = mock(EventSource.class);
        var event = mock(PreInsertEvent.class);
        when(event.getEntity()).thenReturn(entity);
        when(event.getSession()).thenReturn(session);

        listener.onPreInsert(event);

        assertEquals(NOW, entity.getUpdatedAt());
        assertEquals(NOW, entity.getCreatedAt());
    }
}
package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.PersinateEntity;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class PersinatePreInsertListener implements PreInsertEventListener, PersinateListener {
    private final ListenerLogic listenerLogic;

    public PersinatePreInsertListener(ListenerLogic listenerLogic) {
        this.listenerLogic = listenerLogic;
    }

    @Override
    public void register(EventListenerRegistry eventListenerRegistry) {
        eventListenerRegistry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if (listenerLogic.isLive(event.getEntity())) {
            var entity = (PersinateEntity) event.getEntity();

            entity.setCreatedAt(listenerLogic.getCurrentDateTime());
            entity.setUpdatedAt(entity.getCreatedAt());
        }

        return false;
    }
}

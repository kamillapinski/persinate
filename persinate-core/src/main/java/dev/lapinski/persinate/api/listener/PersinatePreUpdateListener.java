package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.PersinateEntity;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

public class PersinatePreUpdateListener implements PreUpdateEventListener, PersinateListener {
    private final ListenerLogic listenerLogic;

    public PersinatePreUpdateListener(ListenerLogic listenerLogic) {
        this.listenerLogic = listenerLogic;
    }

    @Override
    public void register(EventListenerRegistry eventListenerRegistry) {
        eventListenerRegistry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(this);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (listenerLogic.isLive(event.getEntity())) {
            ((PersinateEntity)event.getEntity()).setUpdatedAt(listenerLogic.getCurrentDateTime());
        }

        return false;
    }
}

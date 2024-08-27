package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.Operation;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;

public class PersinatePostInsertListener implements PostInsertEventListener, PersinateListener {
    private final ListenerLogic listenerLogic;

    public PersinatePostInsertListener(ListenerLogic listenerLogic) {
        this.listenerLogic = listenerLogic;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        listenerLogic.generateHstEntity(event::getEntity, event.getSession(), Operation.CREATE);
    }

    @Override
    public void register(EventListenerRegistry eventListenerRegistry) {
        eventListenerRegistry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}

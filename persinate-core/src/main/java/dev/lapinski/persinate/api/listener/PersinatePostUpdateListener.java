package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.Operation;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class PersinatePostUpdateListener implements PostUpdateEventListener, PersinateListener {
    private final ListenerLogic listenerLogic;

    public PersinatePostUpdateListener(ListenerLogic listenerLogic) {
        this.listenerLogic = listenerLogic;
    }

    @Override
    public void register(EventListenerRegistry eventListenerRegistry) {
        eventListenerRegistry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        listenerLogic.generateHstEntity(event::getEntity, event.getSession(), Operation.UPDATE);
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}

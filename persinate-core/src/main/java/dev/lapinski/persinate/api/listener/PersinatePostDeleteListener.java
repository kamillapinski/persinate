package dev.lapinski.persinate.api.listener;

import dev.lapinski.persinate.api.Operation;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class PersinatePostDeleteListener implements PostDeleteEventListener, PersinateListener {
    private final ListenerLogic listenerLogic;

    public PersinatePostDeleteListener(ListenerLogic listenerLogic) {
        this.listenerLogic = listenerLogic;
    }

    @Override
    public void register(EventListenerRegistry eventListenerRegistry) {
        eventListenerRegistry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        listenerLogic.generateHstEntity(event::getEntity, event.getSession(), Operation.DELETE);
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}

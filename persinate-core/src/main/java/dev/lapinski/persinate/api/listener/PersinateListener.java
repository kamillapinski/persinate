package dev.lapinski.persinate.api.listener;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;

import java.io.Serializable;

public interface PersinateListener extends Serializable {
    void register(EventListenerRegistry eventListenerRegistry);

    default void register(SessionFactory sessionFactory) {
        var serviceRegistry = ((SessionFactoryImplementor) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
        register(serviceRegistry);
    }
}

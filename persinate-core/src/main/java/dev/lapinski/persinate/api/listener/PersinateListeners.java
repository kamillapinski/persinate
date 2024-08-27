package dev.lapinski.persinate.api.listener;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;

import java.util.Collection;
import java.util.List;

public final class PersinateListeners {
    public static Collection<PersinateListener> getListeners(ListenerLogic listenerLogic) {
        return List.of(
            new PersinatePreInsertListener(listenerLogic),
            new PersinatePostInsertListener(listenerLogic),
            new PersinatePostUpdateListener(listenerLogic),
            new PersinatePostDeleteListener(listenerLogic),
            new PersinatePreUpdateListener(listenerLogic)
        );
    }

    public static final Collection<PersinateListener> PERSINATE_LISTENERS = getListeners(ListenerLogic.INSTANCE);

    public static void registerPersinateListeners(EntityManagerFactory emf) {
        registerPersinateListeners(emf, PERSINATE_LISTENERS);
    }
    public static void registerPersinateListeners(EntityManagerFactory emf, Iterable<PersinateListener> listeners) {
        var sessionFactory = emf.unwrap(SessionFactoryImpl.class);

        registerPersinateListeners(sessionFactory, listeners);
    }

    public static void registerPersinateListeners(SessionFactory sessionFactory, Iterable<PersinateListener> listeners) {
        listeners.forEach(l -> l.register(sessionFactory));
    }

    public static void registerPersinateListeners(SessionFactory sessionFactory) {
        registerPersinateListeners(sessionFactory, PERSINATE_LISTENERS);
    }
}

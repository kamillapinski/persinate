package dev.lapinski.persinate.example;

import dev.lapinski.persinate.api.listener.ListenerLogic;
import dev.lapinski.persinate.api.listener.PersinateClock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static dev.lapinski.persinate.api.listener.PersinateListeners.getListeners;
import static dev.lapinski.persinate.api.listener.PersinateListeners.registerPersinateListeners;

@DataJpaTest
public abstract class TestBase implements InitializingBean {
    @PersistenceUnit
    protected EntityManagerFactory entityManagerFactory;

    protected LocalDateTime NOW = LocalDateTime.of(2020, 1, 1, 12, 0);

    private final PersinateClock clock = () -> NOW;

    @Override
    public void afterPropertiesSet() {
        registerPersinateListeners(entityManagerFactory, getListeners(ListenerLogic.forClock(clock)));
    }
}

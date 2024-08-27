package dev.lapinski.persinate.api.listener;

import java.time.LocalDateTime;

@FunctionalInterface
public interface PersinateClock {
    LocalDateTime now();

    PersinateClock DEFAULT = LocalDateTime::now;
}

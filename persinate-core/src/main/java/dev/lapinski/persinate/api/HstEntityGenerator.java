package dev.lapinski.persinate.api;

import java.time.LocalDateTime;

public interface HstEntityGenerator<Live, Hst> {
    Hst generateHstEntity(Live liveEntity, Operation operation, LocalDateTime now);
}

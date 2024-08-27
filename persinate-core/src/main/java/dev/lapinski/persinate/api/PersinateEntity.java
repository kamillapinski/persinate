package dev.lapinski.persinate.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class PersinateEntity {
    @Uuid7Generated
    @Id
    @Column(name = PersinateColumns.UUID)
    protected UUID uuid;

    @Version
    @Column(name = PersinateColumns.VERSION)
    protected Long version;

    @Column(name = PersinateColumns.CREATED_AT, nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = PersinateColumns.UPDATED_AT, nullable = false)
    protected LocalDateTime updatedAt;

    @Override
    public int hashCode() {
        return Objects.hash(uuid, version, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        PersinateEntity other = (PersinateEntity) obj;
        return Objects.equals(uuid, other.uuid) && Objects.equals(version, other.version);
    }

    @Override
    public String toString() {
        return getClass() + " {UUID=%s, version=%d}".formatted(uuid, version);
    }
}

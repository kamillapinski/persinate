package dev.lapinski.persinate.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Immutable
public abstract class PersinateHstEntity<T extends PersinateEntity> extends PersinateEntity {
    @JoinColumn(name = PersinateColumns.Hst.LIVE_UUID, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(optional = true)
    protected T liveEntity;

    @Column(name = PersinateColumns.Hst.TRANSACTION_TIMESTAMP, nullable = false)
    protected LocalDateTime transactionTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = PersinateColumns.Hst.OPERATION, nullable = false, length = 6)
    protected Operation operation;
}

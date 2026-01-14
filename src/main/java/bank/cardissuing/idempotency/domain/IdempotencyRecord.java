package bank.cardissuing.idempotency.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "idempotency_records")
@Entity
public class IdempotencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    private String responsePayload;

    private LocalDateTime createdAt;

    public String getResponsePayload() {
        return responsePayload;
    }

    public IdempotencyRecord(Long id, LocalDateTime createdAt, String responsePayload, String idempotencyKey) {
        this.id = id;
        this.createdAt = createdAt;
        this.responsePayload = responsePayload;
        this.idempotencyKey = idempotencyKey;
    }

    public IdempotencyRecord(String idempotencyKey, String responsePayload) {
        this.idempotencyKey = idempotencyKey;
        this.responsePayload = responsePayload;
    }

    public IdempotencyRecord() {
    }

    @PrePersist
    protected void onCreated() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        throw new UnsupportedOperationException("IdempotencyRecord are immutable and cannot be updated.");
    }

    @PreRemove
    protected void onRemove() {
        throw new UnsupportedOperationException("IdempotencyRecord are immutable and cannot be deleted.");
    }
}

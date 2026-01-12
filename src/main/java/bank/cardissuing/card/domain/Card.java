package bank.cardissuing.card.domain;

import bank.cardissuing.customer.domain.BaseEntity;
import bank.cardissuing.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "cards")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String last4;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private LocalDate expiryDate;

    public void activate() {
        if (this.status != CardStatus.CREATED) {
            throw new IllegalStateException("Cannot activate card from status: " + this.status);
        }
        this.status = CardStatus.ACTIVE;
    }

    public void suspend() {
        if (this.status != CardStatus.ACTIVE) {
            throw new IllegalStateException("Cannot suspend card from status: " + this.status);
        }
        this.status = CardStatus.SUSPENDED;
    }

    public void block() {
        if (this.status != CardStatus.ACTIVE && this.status != CardStatus.SUSPENDED) {
            throw new IllegalStateException("Cannot block card from status: " + this.status);
        }
        this.status = CardStatus.BLOCKED;
    }

    public void close() {
        if (this.status == CardStatus.BLOCKED || this.status == CardStatus.SUSPENDED) {
            this.status = CardStatus.CLOSED;
        } else {
            throw new IllegalStateException("Cannot close card from status: " + this.status);
        }
    }

    public void validateForAuthorization() {
        if (this.status != CardStatus.ACTIVE) {
            throw new IllegalStateException("Card is not active. Current status: " + this.status);
        }
        if (this.expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Card is expired.");
        }
    }
}

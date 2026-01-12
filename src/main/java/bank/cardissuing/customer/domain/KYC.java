package bank.cardissuing.customer.domain;

import bank.cardissuing.customer.exception.InvalidStateTransitionException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "kyc")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KYC extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private KYCStatus status;

    private String documentType;
    private String documentNumber;
    private LocalDateTime verifiedAt;
    private LocalDateTime expiresAt;

    public void verify() {
        if (this.status != KYCStatus.PENDING) {
            throw new InvalidStateTransitionException(
                    "Cannot verify KYC from status: " + this.status);
        }
        this.status = KYCStatus.VERIFIED;
        this.verifiedAt = LocalDateTime.now();
    }

    public void reject() {
        if (this.status != KYCStatus.PENDING) {
            throw new InvalidStateTransitionException("Cannot reject KYC from status: " +
                    this.status);
        }
        this.status = KYCStatus.REJECTED;
    }

}

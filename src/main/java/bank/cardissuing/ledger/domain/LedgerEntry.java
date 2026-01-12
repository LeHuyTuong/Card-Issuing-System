package bank.cardissuing.ledger.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// JPA/Hibernate + subclass gọi đc, code business không gọi được
@Getter
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_account_id", nullable = false)
    private LedgerAccount ledgerAccount;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @Column(nullable = false, precision = 19, scale = 2, updatable = false) // ngăn chặn Update tu DB
    private BigDecimal amount;

    private String reference;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public LedgerEntry(LedgerAccount ledgerAccount, EntryType entryType, BigDecimal amount, String reference) {
        this.ledgerAccount = ledgerAccount;
        this.entryType = entryType;
        this.amount = amount;
        this.reference = reference;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        throw new UnsupportedOperationException("LedgerEntry records are immutable and cannot be updated.");
    }

    @PreRemove
    protected void onRemove() {
        throw new UnsupportedOperationException("LedgerEntry records are immutable and cannot be deleted.");
    }
}

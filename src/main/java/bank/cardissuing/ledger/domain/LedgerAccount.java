package bank.cardissuing.ledger.domain;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.customer.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ledger_accounts")
@NoArgsConstructor // ai cũng gọi đc constructor ko tham số
@AllArgsConstructor
@Getter
@Setter
public class LedgerAccount extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(nullable = false)
    private String currency = "VND";
}

package bank.cardissuing.ledger.application;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.ledger.domain.LedgerAccount;

import java.math.BigDecimal;

public interface LedgerService {
    LedgerAccount getLedgerAccount(Long ledgerAccountId);

    BigDecimal getBalance(Long ledgerAccountId);

    void debit(Long ledgerAccountId, BigDecimal amount, String reference, String description);

    LedgerAccount getLedgerAccountByCardId(Card card);
}

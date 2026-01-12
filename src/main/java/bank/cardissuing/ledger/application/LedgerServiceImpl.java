package bank.cardissuing.ledger.application;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.ledger.domain.EntryType;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import bank.cardissuing.ledger.exception.LedgerAccountNotFoundException;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import bank.cardissuing.ledger.infrastructure.LedgerEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final LedgerAccountRepository ledgerAccountRepository;

    @Override
    public LedgerAccount getLedgerAccount(Long ledgerAccountId) {
        return ledgerAccountRepository.findById(ledgerAccountId)
                .orElseThrow(() -> new LedgerAccountNotFoundException("Ledger Account Not Found"));
    }

    @Override
    public BigDecimal getBalance(Long ledgerAccountId) {
        LedgerAccount ledgerAccount = getLedgerAccount(ledgerAccountId);
        return ledgerEntryRepository.calculateBalance(ledgerAccount);
    }

    @Override
    @Transactional
    public void debit(Long ledgerAccountId, BigDecimal amount, String reference, String description) {
        LedgerAccount ledgerAccount = ledgerAccountRepository.findByIdWithLock(ledgerAccountId)
                .orElseThrow(() -> new LedgerAccountNotFoundException("Ledger Account Not Found"));
        BigDecimal balance = getBalance(ledgerAccountId);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        LedgerEntry entry = new LedgerEntry(ledgerAccount, EntryType.DEBIT, amount, description);
        ledgerEntryRepository.save(entry);

    }

    @Override
    public LedgerAccount getLedgerAccountByCardId(Card card) {
        return ledgerAccountRepository.findByCard(card)
                .orElseThrow(() -> new LedgerAccountNotFoundException(
                        "Ledger Account Not Found for Card ID: " + card.getId()));
    }
}

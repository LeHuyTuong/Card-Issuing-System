package bank.cardissuing.ledger.application;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.common.exception.InsufficientFundsException;
import bank.cardissuing.common.exception.ResourceNotFoundException;
import bank.cardissuing.ledger.domain.EntryType;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import bank.cardissuing.ledger.infrastructure.LedgerEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final LedgerAccountRepository ledgerAccountRepository;

    @Override
    public LedgerAccount getLedgerAccount(Long ledgerAccountId) {
        return ledgerAccountRepository.findById(ledgerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("LedgerAccount", "id", ledgerAccountId));
    }

    @Override
    public BigDecimal getBalance(Long ledgerAccountId) {
        LedgerAccount ledgerAccount = getLedgerAccount(ledgerAccountId);
        return ledgerEntryRepository.calculateBalance(ledgerAccount);
    }

    @Override
    @Transactional
    public void debit(Long ledgerAccountId, BigDecimal amount, String reference, String description) {
        // Method guard
        Objects.requireNonNull(ledgerAccountId, "Ledger Account ID must not be null");
        Objects.requireNonNull(amount, "Amount must not be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("Reference is required for debit transactions");
        }

        log.info("Processing debit: accountId={}, amount={}, reference={}", ledgerAccountId, amount, reference);

        LedgerAccount ledgerAccount = ledgerAccountRepository.findByIdWithLock(ledgerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("LedgerAccount", "id", ledgerAccountId));

        BigDecimal balance = getBalance(ledgerAccountId);

        if (amount.compareTo(balance) > 0) {
            log.warn("Insufficient funds: accountId={}, requested={}, available={}",
                    ledgerAccountId, amount, balance);
            throw new InsufficientFundsException(amount, balance);
        }

        LedgerEntry entry = new LedgerEntry(ledgerAccount, EntryType.DEBIT, amount, reference);
        // Note: description param was unused in original code but passed to constructor
        // in previous versions?
        // Checking LedgerEntry constructor from previous turns... it took reference as
        // last param.
        // Wait, the original code had: new LedgerEntry(ledgerAccount, EntryType.DEBIT,
        // amount, description);
        // But the 4th param name in constructor was 'reference'.
        // Let's standardise: pass reference.

        ledgerEntryRepository.save(entry);

        log.info("Debit completed: accountId={}, amount={}, newBalance={}",
                ledgerAccountId, amount, balance.subtract(amount));

    }

    @Override
    public LedgerAccount getLedgerAccountByCardId(Card card) {
        return ledgerAccountRepository.findByCard(card)
                .orElseThrow(() -> new ResourceNotFoundException("LedgerAccount", "cardId", card.getId()));
    }
}

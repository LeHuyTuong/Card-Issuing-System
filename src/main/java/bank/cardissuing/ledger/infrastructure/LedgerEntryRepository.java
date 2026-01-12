package bank.cardissuing.ledger.infrastructure;

import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByLedgerAccount(LedgerAccount ledgerAccount);

    @Query("SELECT COALESCE(SUM(CASE WHEN le.entryType = 'CREDIT' THEN le.amount ELSE -le.amount END), 0) " +
            "FROM LedgerEntry le WHERE le.ledgerAccount = :account")
    BigDecimal calculateBalance(@Param("account") LedgerAccount ledgerAccount);
}

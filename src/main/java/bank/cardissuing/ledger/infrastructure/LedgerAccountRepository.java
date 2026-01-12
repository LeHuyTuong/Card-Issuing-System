package bank.cardissuing.ledger.infrastructure;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.ledger.domain.LedgerAccount;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, Long> {

    Optional<LedgerAccount> findByCard(Card card);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT la FROM LedgerAccount la WHERE la.id = :id")
    Optional<LedgerAccount> findByIdWithLock(@Param("id") Long id);
}

package bank.cardissuing;

import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.customer.exception.EntityNotFoundException;
import bank.cardissuing.ledger.application.LedgerService;
import bank.cardissuing.ledger.domain.EntryType;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {


    @Mock
    private  LedgerService ledgerService;
    @Mock
    private  CardRepository cardRepository;

    private LedgerAccount ledgerAccount;
    private LedgerEntry ledgerEntry;

    @BeforeEach
    void setUp() {
        ledgerAccount = new LedgerAccount();
        ledgerAccount.setId(1L);
        ledgerEntry = new LedgerEntry(ledgerAccount, EntryType.CREDIT, BigDecimal.valueOf(10000), "Initial Deposit");

    }

    @Test
    void authorize_whenCardNotFound_shouldThrow() {

    }
    @Test
    void authorize_whenCardNotActive_shouldThrow() {

    }
    @Test
    void authorize_whenInsufficientFunds_shouldThrow() {

    }
    @Test
    void authorize_whenSuccess_shouldReturnApproveResponse() {

    }
}

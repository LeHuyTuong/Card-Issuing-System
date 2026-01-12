package bank.cardissuing;

import bank.cardissuing.ledger.application.LedgerServiceImpl;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import bank.cardissuing.ledger.exception.LedgerAccountNotFoundException;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import bank.cardissuing.ledger.infrastructure.LedgerEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class LedgerServiceImplTest {
    @Mock
    private LedgerAccountRepository ledgerAccountRepository;
    @Mock
    private LedgerEntryRepository ledgerEntryRepository;

    @InjectMocks
    private LedgerServiceImpl ledgerService;

    private LedgerAccount ledgerAccount;

    @BeforeEach
    void setUp() {
        ledgerAccount = new LedgerAccount();
        ledgerAccount.setId(1L);
        ledgerAccount.setCurrency("VND");

    }

    @Test
    void getBalance_withCreditsAndDebits_shouldCalculateCorrectly() {
        when(ledgerAccountRepository.findById(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));

        when(ledgerEntryRepository.calculateBalance(ledgerAccount)).thenReturn(new BigDecimal("60.00"));

        BigDecimal balance = ledgerService.getBalance(ledgerAccount.getId());
        assertEquals(new BigDecimal("60.00"), balance);
    }

    @Test
    void getBalance_withNoEntries_shouldReturnZero() {
        when(ledgerAccountRepository.findById(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerEntryRepository.calculateBalance(ledgerAccount)).thenReturn(new BigDecimal("0"));
        BigDecimal balance = ledgerService.getBalance(ledgerAccount.getId());
        assertEquals(BigDecimal.ZERO, balance);
    }

    @Test
    void getBalance_whenAccountNotFound_shouldThrowLedgerAccountNotFoundException() {
        when(ledgerAccountRepository.findById(999L)).thenReturn(Optional.empty());

        LedgerAccountNotFoundException ex = assertThrows(LedgerAccountNotFoundException.class,
                () -> ledgerService.getBalance(999L));
        assertEquals("Ledger Account Not Found", ex.getMessage());
        verify(ledgerEntryRepository, never()).findByLedgerAccount(org.mockito.ArgumentMatchers.any());
    }

    // Vì debit() trả về void → không có giá trị để assertEquals.
    // Nên phải kiểm tra hành vi: "Có tạo LedgerEntry và save vào DB không?"
    @Test
    void debit_whenSuccess_shouldSaveEntry() {
        when(ledgerAccountRepository.findByIdWithLock(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerAccountRepository.findById(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerEntryRepository.calculateBalance(ledgerAccount)).thenReturn(new BigDecimal("60.00"));

        ledgerService.debit(1L, new BigDecimal("50"), "ref", "desc");
        verify(ledgerEntryRepository).save(any(LedgerEntry.class));
    }

    @Test
    void debit_whenInsufficientFunds_shouldThrow() {
        when(ledgerAccountRepository.findByIdWithLock(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerAccountRepository.findById(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerEntryRepository.calculateBalance(ledgerAccount)).thenReturn(new BigDecimal("120.00"));
        assertThrows(IllegalArgumentException.class,
                () -> ledgerService.debit(1L, new BigDecimal("150"), "ref", "desc"));
        verify(ledgerEntryRepository, never()).save(any(LedgerEntry.class));
    }

    @Test
    void debit_whenAmountZeroOrNegative_shouldThrow() {
        when(ledgerAccountRepository.findByIdWithLock(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerAccountRepository.findById(ledgerAccount.getId())).thenReturn(Optional.of(ledgerAccount));
        when(ledgerEntryRepository.calculateBalance(ledgerAccount)).thenReturn(new BigDecimal("100.00"));
        assertThrows(IllegalArgumentException.class,
                () -> ledgerService.debit(1L, new BigDecimal("-10"), "ref", "desc"));
        verify(ledgerEntryRepository, never()).save(any(LedgerEntry.class));
    }

}

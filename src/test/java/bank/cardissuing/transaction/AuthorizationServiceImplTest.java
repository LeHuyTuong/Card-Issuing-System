package bank.cardissuing.transaction;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.domain.CardStatus;
import bank.cardissuing.card.exception.InvalidStateTransactionException;
import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.customer.domain.Customer;
import bank.cardissuing.ledger.application.LedgerService;
import bank.cardissuing.ledger.domain.EntryType;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import bank.cardissuing.transaction.application.AuthorizationServiceImpl;
import bank.cardissuing.transaction.domain.AuthorizationRequest;
import bank.cardissuing.transaction.domain.AuthorizationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {


    @Mock
    private  LedgerService ledgerService;
    @Mock // trả về null hoặc default, KHÔNG chạy code thật

    private  CardRepository cardRepository;

    @InjectMocks // móc service để test class thật
    private AuthorizationServiceImpl authorizationService;

    // tạo object thật trong set up khỏi cần mock
    private AuthorizationRequest request;


    private LedgerAccount ledgerAccount;
    private LedgerEntry ledgerEntry;

    @BeforeEach
    void setUp() {
        ledgerAccount = new LedgerAccount();
        ledgerAccount.setId(1L);
        ledgerEntry = new LedgerEntry(ledgerAccount, EntryType.CREDIT, BigDecimal.valueOf(10000), "Initial Deposit");
        request = new AuthorizationRequest();
        request.setCardId(1L);
        request.setAmount(BigDecimal.valueOf(100));
        request.setMerchantName("Test Merchant");
    }

    @Test
    void authorize_whenCardNotFound_shouldThrow() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidStateTransactionException.class, () -> authorizationService.authorize(request));
        verify(cardRepository).findById(1L);
        verifyNoMoreInteractions(cardRepository); // card not found , ko debit
    }
    @Test
    void authorize_whenCardNotActive_shouldThrow() {
        Card card = new Card(new Customer(), "hehe", CardStatus.BLOCKED, LocalDate.now());
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class, () -> authorizationService.authorize(request));
        verify(cardRepository).findById(1L);
        verifyNoMoreInteractions(cardRepository);
    }
    @Test
    void authorize_whenInsufficientFunds_shouldThrow() {
        Card card = new Card(new Customer(), "hehe", CardStatus.ACTIVE, LocalDate.now().plusYears(1));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(ledgerService.getLedgerAccountByCardId(card)).thenReturn(ledgerAccount);


        //Mock debit() để throw khi được gọi

        doThrow(new IllegalArgumentException("Insufficient funds"))
                .when(ledgerService).debit(anyLong(), any(), anyString(), anyString());

        assertThrows(IllegalArgumentException.class, () -> authorizationService.authorize(request));

        verify(cardRepository).findById(1L);
        verifyNoMoreInteractions(cardRepository);
    }
    @Test
    void authorize_whenSuccess_shouldReturnApproveResponse() {
        Card card = new Card(new Customer(), "hehe", CardStatus.ACTIVE, LocalDate.now().plusYears(1));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(ledgerService.getLedgerAccountByCardId(card)).thenReturn(ledgerAccount);
        when(ledgerService.getBalance(ledgerAccount.getId())).thenReturn(BigDecimal.valueOf(10000));

        AuthorizationResponse response = authorizationService.authorize(request);

        assertNotNull(response);
        assertTrue(response.isApproved());
        assertEquals("00", response.getResponseCode());
        assertTrue(response.getApprovalCode().startsWith("AUTH-"));
        assertEquals(BigDecimal.valueOf(10000), response.getAmount());



        verify(cardRepository).findById(1L);
        verify(ledgerService).debit(
                eq(ledgerAccount.getId()),
                eq(request.getAmount()),
                eq(request.getMerchantName()),
                anyString());
    }
}

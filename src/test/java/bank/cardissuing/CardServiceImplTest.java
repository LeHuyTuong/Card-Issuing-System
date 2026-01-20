package bank.cardissuing;

import bank.cardissuing.card.application.CardServiceImpl;
import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.domain.CardStatus;
import bank.cardissuing.card.exception.KycNotVerifiedException;
import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.customer.domain.Customer;
import bank.cardissuing.customer.domain.KYC;
import bank.cardissuing.customer.domain.KYCStatus;
import bank.cardissuing.customer.exception.EntityNotFoundException;
import bank.cardissuing.customer.infrastructure.CustomerRepository;
import bank.cardissuing.customer.infrastructure.KYCRepository;
import bank.cardissuing.idempotency.application.IdempotencyService;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private KYCRepository kycRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LedgerAccountRepository ledgerAccountRepository;
    @Mock
    private IdempotencyService idempotencyService;
    @Mock
    private ObjectMapper objectMapper;

    // Mockito cần biết class thật để inject các @Mock vào.
    // Interface không có implementation để inject.
    @InjectMocks
    private CardServiceImpl cardService;

    private Customer customer;
    private KYC kyc;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        kyc = new KYC();
        kyc.setCustomer(customer);
        kyc.setStatus(KYCStatus.VERIFIED);
    }

    @Test
    void issueCard_whenCustomerNotFound_shouldThrowEntityNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardService.issueCard(1L, "dummy-key"));

        verify(customerRepository).findById(1L);
        verifyNoMoreInteractions(customerRepository, kycRepository, cardRepository);
    }

    @Test
    void issueCard_whenKycMissing_shouldThrowKycNotVerified() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(kycRepository.findByCustomer(customer)).thenReturn(Optional.empty());
        // Mock checking idempotency first as per new flow
        when(idempotencyService.getExistingResponse("dummy-key")).thenReturn(Optional.empty());

        assertThrows(KycNotVerifiedException.class, () -> cardService.issueCard(1L, "dummy-key"));
    }

    @Test
    void issueCard_whenKycNotVerified_shouldThrowKycNotVerified() {
        kyc.setStatus(KYCStatus.REJECTED);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(kycRepository.findByCustomer(customer)).thenReturn(Optional.of(kyc));
        when(idempotencyService.getExistingResponse("dummy-key")).thenReturn(Optional.empty());

        assertThrows(KycNotVerifiedException.class, () -> cardService.issueCard(1L, "dummy-key"));
    }

    @Test
    void issueCard_whenValid_shouldPersistCardWithExpectedFields() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(kycRepository.findByCustomer(customer)).thenReturn(Optional.of(kyc));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(idempotencyService.getExistingResponse("dummy-key")).thenReturn(Optional.empty());
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");

        Card saved = cardService.issueCard(1L, "dummy-key");

        assertNotNull(saved);
        assertEquals(customer, saved.getCustomer());
        assertEquals(CardStatus.CREATED, saved.getStatus());
        assertNotNull(saved.getLast4());
        assertEquals(4, saved.getLast4().length());
        assertTrue(saved.getLast4().matches("\\d{4}"));
        assertEquals(LocalDate.now().plusYears(1), saved.getExpiryDate());

        verify(cardRepository).save(any(Card.class));
        verify(ledgerAccountRepository).save(any());
        verify(idempotencyService).saveResponse(eq("dummy-key"), anyString());
    }

    @Test
    void issueCard_shouldGenerateDifferentLast4AcrossCalls_statisticallyLikely() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(kycRepository.findByCustomer(customer)).thenReturn(Optional.of(kyc));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(idempotencyService.getExistingResponse(anyString())).thenReturn(Optional.empty());
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");

        Card c1 = cardService.issueCard(1L, "key-1");
        Card c2 = cardService.issueCard(1L, "key-2");

        assertNotNull(c1.getLast4());
        assertNotNull(c2.getLast4());
        assertNotEquals(c1.getLast4(), c2.getLast4(), "Two issued cards should not consistently have the same last4");
    }
}

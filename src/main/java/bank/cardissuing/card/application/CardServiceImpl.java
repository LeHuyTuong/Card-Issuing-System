package bank.cardissuing.card.application;

import bank.cardissuing.audit.application.AuditService;
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
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final KYCRepository kycRepository;
    private final CustomerRepository customerRepository;
    private final LedgerAccountRepository ledgerAccountRepository;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;
    private final AuditService auditService;

    @Override
    @Transactional
    public Card issueCard(Long customerId, String idempotencyKey) {
        log.info("Attempting to issue card: customerId={}, idempotencyKey={}", customerId, idempotencyKey);

        Optional<String> existingJson = idempotencyService.getExistingResponse(idempotencyKey);
        if (existingJson.isPresent()) {
            log.info("Returning cached card response for idempotencyKey={}", idempotencyKey);
            try {
                return objectMapper.readValue(existingJson.get(), Card.class);
            } catch (Exception e) {
                log.error("Failed to deserialize cached card response for key={}", idempotencyKey, e);
                throw new RuntimeException("Failed to deserialize cached card response", e);
            }
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found ID: " + customerId));

        KYC kyc = kycRepository.findByCustomer(customer)
                .orElseThrow(() -> new KycNotVerifiedException("KYC Not Found for Customer ID: " + customerId));

        if (kyc.getStatus() != KYCStatus.VERIFIED) {
            throw new KycNotVerifiedException("KYC Not Verified for Customer ID: " + customerId);
        }

        Card card = new Card();
        card.setCustomer(customer);
        card.setLast4(generateLast4());
        card.setStatus(CardStatus.CREATED);
        card.setExpiryDate(LocalDate.now().plusYears(1));

        Card savedCard = cardRepository.save(card);

        LedgerAccount account = new LedgerAccount();
        account.setCard(savedCard);
        account.setCurrency("VND");
        ledgerAccountRepository.save(account);

        try {
            String json = objectMapper.writeValueAsString(savedCard);
            idempotencyService.saveResponse(idempotencyKey, json);
        } catch (Exception e) {
            log.error("Failed to serialize card response for cardId={}", savedCard.getId(), e);
            throw new RuntimeException("Failed to serialize card response", e);
        }

        auditService.log("ISSUE_CARD", "Card", savedCard.getId().toString(), "SYSTEM");

        log.info("Card issued successfully: cardId={}, customerId={}, last4={}",
                savedCard.getId(), customerId, savedCard.getLast4());

        return savedCard;
    }

    private String generateLast4() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }
}

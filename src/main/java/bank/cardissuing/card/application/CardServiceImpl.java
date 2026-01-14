package bank.cardissuing.card.application;

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
import bank.cardissuing.idempotency.application.IdempotencyServiceImpl;
import bank.cardissuing.idempotency.domain.IdempotencyRecord;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final KYCRepository kycRepository;
    private final CustomerRepository customerRepository;
    private final LedgerAccountRepository ledgerAccountRepository;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Card issueCard(Long customerId, String idempotencyKey) {
        Optional<String> existingJson = idempotencyService.getExistingResponse(idempotencyKey);
        if (existingJson.isPresent()) {
            try {
                return objectMapper.readValue(existingJson.get(), Card.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize cached card response", e);
            }
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));

        KYC kyc = kycRepository.findByCustomer(customer)
                .orElseThrow(() -> new KycNotVerifiedException("KYC Not Found"));

        if (kyc.getStatus() != KYCStatus.VERIFIED) {
            throw new KycNotVerifiedException("KYC Not Verified");
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
            throw new RuntimeException("Failed to serialize card response", e);
        }

        return savedCard;
    }

    private String generateLast4() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }
}

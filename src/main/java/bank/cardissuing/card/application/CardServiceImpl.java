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
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final KYCRepository kycRepository;
    private final CustomerRepository customerRepository;
    private final LedgerAccountRepository ledgerAccountRepository;

    @Override
    @Transactional
    public Card issueCard(Long customerId) {
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

        LedgerAccount account = new LedgerAccount();
        account.setCard(card);
        account.setCurrency("VND");
        ledgerAccountRepository.save(account);
        return cardRepository.save(card);
    }

    private String generateLast4() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }
}

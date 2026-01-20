package bank.cardissuing.common.bootstrap;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.domain.CardStatus;
import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.customer.domain.Customer;
import bank.cardissuing.customer.domain.KYC;
import bank.cardissuing.customer.domain.KYCStatus;
import bank.cardissuing.customer.infrastructure.CustomerRepository;
import bank.cardissuing.customer.infrastructure.KYCRepository;
import bank.cardissuing.ledger.domain.EntryType;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.ledger.domain.LedgerEntry;
import bank.cardissuing.ledger.infrastructure.LedgerAccountRepository;
import bank.cardissuing.ledger.infrastructure.LedgerEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Component
@Profile("!test") // Don't run this in unit tests
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final KYCRepository kycRepository;
    private final CardRepository cardRepository;
    private final LedgerAccountRepository ledgerAccountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() > 0) {
            log.info("Database already seeded. Skipping data initialization.");
            return;
        }

        log.info("Starting data seeding...");

        // 1. Create Customer
        Customer customer = new Customer();
        customer.setFullName("Nguyen Van A");
        customer.setPhoneNumber("0987654321");
        customer = customerRepository.save(customer);
        log.info("Seeded Customer: ID={}", customer.getId());

        // 2. Create KYC
        KYC kyc = new KYC();
        kyc.setCustomer(customer);
        kyc.setStatus(KYCStatus.VERIFIED);
        kycRepository.save(kyc);
        log.info("Seeded KYC: VERIFIED");

        // 3. Create Card (Active)
        Card card = new Card();
        card.setCustomer(customer);
        card.setLast4("1234");
        card.setStatus(CardStatus.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(3));
        card = cardRepository.save(card);
        log.info("Seeded Card: ID={}, Status=ACTIVE", card.getId());

        // 4. Create Ledger Account
        LedgerAccount account = new LedgerAccount();
        account.setCard(card);
        account.setCurrency("VND");
        account = ledgerAccountRepository.save(account);
        log.info("Seeded LedgerAccount: ID={}", account.getId());

        // 5. Create Initial Balance (5,000,000 VND)
        LedgerEntry entry = new LedgerEntry(account, EntryType.CREDIT, new BigDecimal("5000000"), "Initial Deposit");
        ledgerEntryRepository.save(entry);
        log.info("Seeded Balance: 5,000,000 VND");

        log.info("Data seeding completed successfully!");
    }
}

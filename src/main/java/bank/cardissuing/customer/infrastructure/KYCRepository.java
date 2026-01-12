package bank.cardissuing.customer.infrastructure;

import bank.cardissuing.customer.domain.Customer;
import bank.cardissuing.customer.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Long> {
    Optional<KYC> findByCustomer(Customer customer);
}

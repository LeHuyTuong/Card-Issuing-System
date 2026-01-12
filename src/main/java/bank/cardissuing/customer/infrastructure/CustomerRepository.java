package bank.cardissuing.customer.infrastructure;

import bank.cardissuing.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

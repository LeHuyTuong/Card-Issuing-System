package bank.cardissuing.card.infrastructure;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Object findByCustomer(Customer customer);
}

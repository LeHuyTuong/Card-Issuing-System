package bank.cardissuing.card.application;

import bank.cardissuing.card.domain.Card;

public interface CardService {
    Card issueCard(Long customerId);
}

package bank.cardissuing.transaction.application;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.exception.InvalidStateTransactionException;
import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.ledger.application.LedgerService;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.transaction.domain.AuthorizationRequest;
import bank.cardissuing.transaction.domain.AuthorizationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final LedgerService ledgerService;
    private final CardRepository cardRepository;

    @Transactional
    public AuthorizationResponse authorize(AuthorizationRequest request) {
        // B1 đi tìm card
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new InvalidStateTransactionException("Card Not Found"));
        card.validateForAuthorization();

        LedgerAccount ledgerAccount = ledgerService.getLedgerAccountByCardId(card);
        Long ledgerAccountId = ledgerAccount.getId();
        // B2 tim tien tru
        ledgerService.debit(ledgerAccountId, request.getAmount(),
                request.getMerchantName(), "Authorization Debit");
        // Get updated balance
        BigDecimal newBalance = ledgerService.getBalance(ledgerAccountId);

        return AuthorizationResponse.approve("AUTH-" + System.currentTimeMillis(), newBalance);
    }
}

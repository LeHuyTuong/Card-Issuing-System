package bank.cardissuing.transaction.application;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.exception.InvalidStateTransactionException;
import bank.cardissuing.card.infrastructure.CardRepository;
import bank.cardissuing.ledger.application.LedgerService;
import bank.cardissuing.ledger.domain.LedgerAccount;
import bank.cardissuing.transaction.domain.AuthorizationRequest;
import bank.cardissuing.transaction.domain.AuthorizationResponse;
import bank.cardissuing.common.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final LedgerService ledgerService;
    private final CardRepository cardRepository;

    @Transactional
    public AuthorizationResponse authorize(AuthorizationRequest request) {
        log.info("Processing authorization: cardId={}, amount={}, merchant={}",
                request.getCardId(), request.getAmount(), request.getMerchantName());

        // B1 đi tìm card
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", request.getCardId()));

        // Note: card.validateForAuthorization() might throw IllegalStateException.
        // Ideally we should refactor Card.java to throw InvalidCardStateException
        // (extends BusinessException)
        // allowing GlobalExceptionHandler to catch it properly (409 Conflict).
        card.validateForAuthorization();

        LedgerAccount ledgerAccount = ledgerService.getLedgerAccountByCardId(card);
        Long ledgerAccountId = ledgerAccount.getId();

        // B2 tim tien tru
        ledgerService.debit(ledgerAccountId, request.getAmount(),
                request.getMerchantName(), "Authorization Debit");

        // Get updated balance
        BigDecimal newBalance = ledgerService.getBalance(ledgerAccountId);

        AuthorizationResponse response = AuthorizationResponse.approve("AUTH-" + System.currentTimeMillis(),
                newBalance);

        log.info("Authorization approved: cardId={}, approvalCode={}, newBalance={}",
                card.getId(), response.getApprovalCode(), response.getAmount());

        return response;
    }
}

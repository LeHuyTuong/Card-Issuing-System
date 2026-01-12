package bank.cardissuing.transaction.application;

import bank.cardissuing.transaction.domain.AuthorizationRequest;
import bank.cardissuing.transaction.domain.AuthorizationResponse;

public interface AuthorizationService {
    AuthorizationResponse authorize(AuthorizationRequest request);
}

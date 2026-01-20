package bank.cardissuing.card.exception;

import bank.cardissuing.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class KycNotVerifiedException extends BusinessException {
    public KycNotVerifiedException(String message) {
        super("KYC_NOT_VERIFIED", message, HttpStatus.FORBIDDEN);
    }
}

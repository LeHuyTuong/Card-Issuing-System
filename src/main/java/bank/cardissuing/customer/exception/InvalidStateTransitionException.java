package bank.cardissuing.customer.exception;

import bank.cardissuing.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidStateTransitionException extends BusinessException {
    public InvalidStateTransitionException(String message) {
        super("INVALID_STATE_TRANSITION",message, HttpStatus.CONFLICT);
    }
}

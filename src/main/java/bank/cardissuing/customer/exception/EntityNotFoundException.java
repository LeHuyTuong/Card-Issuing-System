package bank.cardissuing.customer.exception;

import bank.cardissuing.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super("ENTITY_NOT_FOUND",message, HttpStatus.NOT_FOUND);
    }
}

package bank.cardissuing.ledger.exception;

import bank.cardissuing.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class LedgerAccountNotFoundException extends BusinessException {
    public LedgerAccountNotFoundException(String message) {
        super("LEDGER_ACCOUNT_NOT_FOUND",message, HttpStatus.NOT_FOUND);
    }
}

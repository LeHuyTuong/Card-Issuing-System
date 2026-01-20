package bank.cardissuing.common.exception;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class InsufficientFundsException extends BusinessException{
    public InsufficientFundsException(BigDecimal requested, BigDecimal available) {
        super("INSUFFICIENT_FUNDS",
                "Insufficient funds: requested " + requested + ", available " + available,
                HttpStatus.BAD_REQUEST);
    }
}

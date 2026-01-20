package bank.cardissuing.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCardStateException extends BusinessException{
    public InvalidCardStateException(String action, String currentState) {
        super("INVALID_CARD_STATE",
                "Cannot perform action '" + action + "' when card is in state '" + currentState + "'.",
                HttpStatus.CONFLICT);
    }
}

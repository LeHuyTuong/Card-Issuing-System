package bank.cardissuing.card.exception;

public class InvalidStateTransactionException extends RuntimeException {
    public InvalidStateTransactionException(String message) {
        super(message);
    }
}

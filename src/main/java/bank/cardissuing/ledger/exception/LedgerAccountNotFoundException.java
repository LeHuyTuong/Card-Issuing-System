package bank.cardissuing.ledger.exception;

public class LedgerAccountNotFoundException extends RuntimeException {
    public LedgerAccountNotFoundException(String message) {
        super(message);
    }
}

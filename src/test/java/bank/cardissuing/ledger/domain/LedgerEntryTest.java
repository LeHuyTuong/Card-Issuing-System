package bank.cardissuing.ledger.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LedgerEntryTest {

    @Test
    void shouldNotAllowUpdate(){
        LedgerEntry entry = new LedgerEntry(null, EntryType.CREDIT, new BigDecimal("100.00"), "VND");
        assertThrows(UnsupportedOperationException.class, () -> {
            entry.onUpdate();
        });
    }
}

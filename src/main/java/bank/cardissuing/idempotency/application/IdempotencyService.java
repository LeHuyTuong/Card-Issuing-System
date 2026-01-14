package bank.cardissuing.idempotency.application;

import java.util.Optional;

public interface IdempotencyService {
    Optional<String> getExistingResponse(String key);
    void saveResponse(String key,String responseJson);
}

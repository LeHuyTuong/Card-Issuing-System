package bank.cardissuing.idempotency.application;

import bank.cardissuing.idempotency.domain.IdempotencyRecord;
import bank.cardissuing.idempotency.infrastructure.IdempotencyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdempotencyServiceImpl implements IdempotencyService {

    private final IdempotencyRecordRepository idempotencyRecordRepository;

    @Override
    public Optional<String> getExistingResponse(String key) {
        return idempotencyRecordRepository.findByIdempotencyKey(key)
                .map(IdempotencyRecord::getResponsePayload);
    }

    @Override
    public void saveResponse(String key, String responseJson) {
        IdempotencyRecord record = new IdempotencyRecord(key, responseJson);
        idempotencyRecordRepository.save(record);
    }

}

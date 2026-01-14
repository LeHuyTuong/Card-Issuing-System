package bank.cardissuing;

import bank.cardissuing.idempotency.application.IdempotencyServiceImpl;
import bank.cardissuing.idempotency.domain.IdempotencyRecord;
import bank.cardissuing.idempotency.infrastructure.IdempotencyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdempotencyServiceImplTest {

    @Mock
    private IdempotencyRecordRepository repository;

    @InjectMocks
    private IdempotencyServiceImpl service;

    @Test
    void getExistingResponse_whenRecordExists_returnsPayload() {
        String key = "idem-123";
        IdempotencyRecord record = new IdempotencyRecord(key, "{\"ok\":true}");
        when(repository.findByIdempotencyKey(key)).thenReturn(Optional.of(record));

        Optional<String> result = service.getExistingResponse(key);

        assertTrue(result.isPresent());
        assertEquals("{\"ok\":true}", result.get());
        verify(repository).findByIdempotencyKey(key);
    }

    @Test
    void getExistingResponse_whenRecordMissing_returnsEmpty() {
        String key = "missing";
        when(repository.findByIdempotencyKey(key)).thenReturn(Optional.empty());

        Optional<String> result = service.getExistingResponse(key);

        assertTrue(result.isEmpty());
        verify(repository).findByIdempotencyKey(key);
    }

    @Test
    void saveResponse_persistsNewRecordWithKeyAndPayload() {
        String key = "save-1";
        String payload = "{}";

        service.saveResponse(key, payload);

        verify(repository).save(argThat(r ->
                r != null &&
                payload.equals(r.getResponsePayload())
        ));
    }

    @Test
    void saveResponse_shouldNotQueryBeforeSave() {
        String key = "no-query";
        String payload = "{1}";

        service.saveResponse(key, payload);

        verify(repository, never()).findByIdempotencyKey(anyString());
        verify(repository).save(any(IdempotencyRecord.class));
    }

}

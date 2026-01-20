package bank.cardissuing.audit.application;

import bank.cardissuing.audit.domain.AuditLog;
import bank.cardissuing.audit.infrastructure.AuditLogRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW) // transaction riêng biệt
    public void log(String action, String entityName, String entityId, String performBy) {
        AuditLog newLog = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .username(performBy)
                .build();

        auditLogRepository.save(newLog);
    }
}

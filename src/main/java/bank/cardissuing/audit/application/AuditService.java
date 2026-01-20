package bank.cardissuing.audit.application;

public interface AuditService {
    void log(String action, String entityName, String entityId, String performBy);
}

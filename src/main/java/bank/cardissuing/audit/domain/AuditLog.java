package bank.cardissuing.audit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "AuditLogs")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String entityName;
    private String entityId;
    private String username;

    //Thêm @Builder.Default vào trường timestamp để khi build nó không bị null.
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}

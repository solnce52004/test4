package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "audit_log_users", schema = "test4")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString

public class AuditLogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "event_msg", nullable = false, length = 255)
    private String eventMsg;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @NotNull
    @Column(name = "entity_class", nullable = false, length = 255)
    private String entityClass;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    public AuditLogUser(String eventMsg, Long entityId, String entityClass, Long userId) {
        this.eventMsg = eventMsg;
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.userId = userId;
    }
}

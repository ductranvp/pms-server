package vn.ptit.qldaserver.domain;

import lombok.*;
import vn.ptit.qldaserver.domain.audit.AuditEvent;
import vn.ptit.qldaserver.domain.enumeration.NotificationType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Notification extends AuditEvent implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @OneToMany(mappedBy = "notification")
    private Set<UserNotification> userNotifications;
}

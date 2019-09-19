package vn.ptit.qldaserver.domain.key;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserInvitationKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "invitation_id")
    private Long invitationId;
}

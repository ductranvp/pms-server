package vn.ptit.pms.domain;

import lombok.*;
import vn.ptit.pms.domain.enumeration.InvitationStatus;
import vn.ptit.pms.domain.key.UserInvitationKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_invitation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserInvitation implements Serializable {
    public static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserInvitationKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("invitation_id")
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.NO_ACTION;

    public UserInvitation(UserInvitationKey userInvitationKey) {
        this.id = userInvitationKey;
    }
}

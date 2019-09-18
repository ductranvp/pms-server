package vn.ptit.qldaserver.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import vn.ptit.qldaserver.domain.enumeration.InvitationStatus;
import vn.ptit.qldaserver.domain.key.UserInvitationKey;

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
    @EmbeddedId
    private UserInvitationKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("invitation_id")
    @JoinColumn(name = "invitation_id")
    @JsonManagedReference
    private Invitation invitation;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.NO_ACTION;
}

package vn.ptit.qldaserver.service.dto;

import lombok.*;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.domain.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserInvitationDto {
    private String inviter;
    private Project project;
    private Invitation invitation;
}

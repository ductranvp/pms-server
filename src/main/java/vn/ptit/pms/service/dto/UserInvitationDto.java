package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Invitation;
import vn.ptit.pms.domain.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserInvitationDto {
    private UserDto inviter;
    private Project project;
    private Invitation invitation;
}

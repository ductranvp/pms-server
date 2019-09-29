package vn.ptit.pms.service.dto;

import lombok.Data;
import vn.ptit.pms.domain.Invitation;
import vn.ptit.pms.domain.Project;

import java.util.Set;

@Data
public class InvitationRequestDto {
    private Long projectId;
    private String content;
    private Set<String> listEmail;

    public Invitation toBean() {
        Invitation invitation = new Invitation();
        invitation.setContent(content);
        invitation.setProject(new Project(projectId));
        return invitation;
    }
}

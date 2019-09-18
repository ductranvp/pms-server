package vn.ptit.qldaserver.service;

import lombok.Data;
import vn.ptit.qldaserver.domain.Invitation;
import vn.ptit.qldaserver.domain.Project;

import java.util.Set;

@Data
public class ProjectInvitationDto {
    private Long projectId;
    private String content;
    private Set<String> listUsers;

    public Invitation toBean(){
        Invitation invitation = new Invitation();
        invitation.setContent(content);
        invitation.setProject(new Project(projectId));
        return invitation;
    }
}

package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserProjectDto {
    private Long projectId;
    private User user;
    private ProjectRole role;

    public UserProject toEntity(){
        UserProject entity = new UserProject();
        entity.setId(new UserProjectKey(this.user.getId(), this.projectId));
        entity.setRole(this.role);
        return entity;
    }

    public static UserProjectDto valueOf(UserProject entity){
        UserProjectDto dto = new UserProjectDto();
        dto.setProjectId(entity.getId().getProjectId());
        dto.setUser(entity.getUser());
        dto.setRole(entity.getRole());
        return dto;
    }
}

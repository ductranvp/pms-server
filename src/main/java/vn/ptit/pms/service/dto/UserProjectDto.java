package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.enumeration.ProjectRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserProjectDto {
    private Long projectId;
    private User user;
    private ProjectRole role;
}

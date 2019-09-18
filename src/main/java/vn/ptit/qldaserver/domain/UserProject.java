package vn.ptit.qldaserver.domain;

import lombok.*;
import vn.ptit.qldaserver.domain.enumeration.ProjectRole;
import vn.ptit.qldaserver.domain.key.UserProjectKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserProject implements Serializable {
    @EmbeddedId
    private UserProjectKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("project_id")
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role = ProjectRole.ROLE_MEMBER;
}

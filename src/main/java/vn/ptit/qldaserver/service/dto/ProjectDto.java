package vn.ptit.qldaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.qldaserver.domain.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;

    public Project toBean(){
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }
}

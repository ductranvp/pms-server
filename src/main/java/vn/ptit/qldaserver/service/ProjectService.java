package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.repository.ProjectRepository;
import vn.ptit.qldaserver.service.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    ProjectRepository projectRepository;

    public Project save(ProjectDto projectDto) {
        Project project = projectDto.toBean();
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public void close(Long id) {
        Project project = projectRepository.findById(id).get();
        project.setClosed(true);
        projectRepository.save(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findOne(Long id) {
        return projectRepository.findById(id);
    }
}

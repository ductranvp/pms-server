package vn.ptit.qldaserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.repository.ProjectRepository;
import vn.ptit.qldaserver.service.ProjectService;
import vn.ptit.qldaserver.service.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project save(ProjectDto projectDto) {
        Project project = projectDto.toBean();
        return projectRepository.save(project);
    }

    @Override
    public void delete(Long id) {
//        Project project = projectRepository.findById(id).get();
//        project.setDeleted(true);
//        projectRepository.save(project);
        projectRepository.deleteById(id);
    }

    @Override
    public void close(Long id) {
        Project project = projectRepository.findById(id).get();
        project.setClosed(true);
        projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findOne(Long id) {
        return projectRepository.findById(id).get();
    }
}

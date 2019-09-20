package vn.ptit.pms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.ProjectRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        try {
            Project project = projectRepository.findById(id).get();
            project.setDeleted(true);
            projectRepository.save(project);
        } catch (NoSuchElementException e) {
            throw new AppException("Project could not be found");
        }
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findOne(Long id) {
        try {
            return projectRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new AppException("Project could not be found");
        }
    }
}

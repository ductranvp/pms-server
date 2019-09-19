package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.ProjectRepository;
import vn.ptit.qldaserver.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

    public void deleteOnDatabase(Long id) {
        projectRepository.deleteById(id);
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

package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.UserProject;
import vn.ptit.qldaserver.domain.enumeration.ProjectRole;
import vn.ptit.qldaserver.domain.key.UserProjectKey;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.UserProjectRepository;

import java.util.List;

@Service
public class UserProjectService {
    private final String ENTITY_NAME = "User Project";
    @Autowired
    UserProjectRepository userProjectRepository;

    public UserProject save(UserProject entity) {
        return userProjectRepository.save(entity);
    }

    public UserProject findOne(UserProjectKey id) {
        try {
            return userProjectRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<UserProject> findByUserId(Long userId) {
        return userProjectRepository.findByIdUserId(userId);
    }

    public List<UserProject> findByProjectId(Long id) {
        return userProjectRepository.findByIdProjectId(id);
    }

    public List<UserProject> findAll() {
        return userProjectRepository.findAll();
    }

    public void delete(UserProjectKey id) {
        userProjectRepository.deleteById(id);
    }

    public void addUserToProject(Long userId, Long projectId) {
        UserProject userProject = new UserProject(new UserProjectKey(userId, projectId));
        userProject.setRole(ProjectRole.ROLE_MEMBER);
        userProjectRepository.save(userProject);
    }
}

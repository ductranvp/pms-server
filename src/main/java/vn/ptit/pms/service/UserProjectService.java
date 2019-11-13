package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.key.UserProjectKey;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.UserProjectRepository;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.service.dto.TotalUserProjectDto;
import vn.ptit.pms.service.dto.UserDto;
import vn.ptit.pms.service.dto.UserProjectDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProjectService {
    private final String ENTITY_NAME = "User Project";
    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectService projectService;

    public boolean isUserInProject(Long userId, Long projectId) {
        return userProjectRepository.findById(new UserProjectKey(userId, projectId)).isPresent();
    }

    public boolean isUserProjectInRole(Long userId, Long projectId, ProjectRole role) {
        return userProjectRepository.findByProjectIdAndUserIdAndRole(projectId, userId, role) != null;
    }

    public UserProject save(UserProject entity) {
        return userProjectRepository.save(entity);
    }

    public UserProject getOneById(UserProjectKey id) {
        try {
            return userProjectRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<UserProject> getByUserId(Long userId) {
        return userProjectRepository.findByIdUserId(userId);
    }

    public List<UserProject> getByProjectId(Long projectId) {
        return userProjectRepository.findByIdProjectId(projectId);
    }

    public List<UserProject> getAll() {
        return userProjectRepository.findAll();
    }

    public void delete(UserProjectKey id) {
        userProjectRepository.deleteById(id);
    }

    public boolean isLastAdmin(UserProjectKey id){
        List<UserProject> list = userProjectRepository.findByProjectIdAndRole(id.getProjectId(), ProjectRole.ROLE_MANAGER);
        boolean isAdmin = projectService.checkProjectAdmin(id.getProjectId(), id.getUserId());
        if (isAdmin && list.size() == 1) return true;
        return false;
    }

    public void addUserToProject(Long userId, Long projectId) {
        UserProject userProject = new UserProject(new UserProjectKey(userId, projectId));
        userProject.setRole(ProjectRole.ROLE_MEMBER);
        userProjectRepository.save(userProject);
    }

    public List<UserProjectDto> getUserProjectByProjectId(Long projectId) {
        List<UserProject> list = getByProjectId(projectId);
        List<UserProjectDto> users = new ArrayList<>();
        list.forEach(userProject -> {
            UserProjectDto dto = new UserProjectDto();
            dto.setProjectId(userProject.getId().getProjectId());
            dto.setUser(userProject.getUser());
            dto.setRole(userProject.getRole());
            users.add(dto);
        });
        return users;
    }

    public List<UserDto> getListUserOfProject(Long projectId){
        List<UserProject> list = getByProjectId(projectId);
        return list.stream().map(userProject -> new UserDto(userProject.getUser())).collect(Collectors.toList());
    }

    public List<Project> getProjectByCurrentUser() {
        List<UserProject> list = getByUserId(userService.getCurrentUser().getId());
        List<Project> projects = new ArrayList<>();
        list.forEach(userProject -> {
            projects.add(userProject.getProject());
        });
        return projects;
    }

    public List<Project> getProjectByCurrentUserWithRoleManager() {
        List<UserProject> list = getByUserId(userService.getCurrentUser().getId());
        List<Project> projects = new ArrayList<>();
        list.forEach(userProject -> {
            if (userProject.getRole().equals(ProjectRole.ROLE_MANAGER)) {
                projects.add(userProject.getProject());
            }
        });
        return projects;
    }

    public List<Project> getProjectByCurrentUserWithRoleMember() {
        List<UserProject> list = getByUserId(userService.getCurrentUser().getId());
        List<Project> projects = new ArrayList<>();
        list.forEach(userProject -> {
            if (userProject.getRole().equals(ProjectRole.ROLE_MEMBER)) {
                projects.add(userProject.getProject());
            }
        });
        return projects;
    }

    public List<Project> getByCloseStatus() {
        return null;
    }

    public List<Project> getProjectByUsername(String username) {
        List<UserProject> currentUserProject = getByUserId(userService.getCurrentUser().getId());
        List<UserProject> queriedUserProject = getByUserId(userRepository.findByUsername(username).get().getId());
        List<Project> projects = new ArrayList<>();

        for (int i = 0; i < currentUserProject.size(); i++) {
            for (int j = 0; j < queriedUserProject.size(); j++) {
                if (currentUserProject.get(i).getProject().getId() == queriedUserProject.get(j).getProject().getId()) {
                    projects.add(currentUserProject.get(i).getProject());
                }
            }
        }
        return projects;
    }

    public TotalUserProjectDto getOverviewByCurrentUser() {
        TotalUserProjectDto result = new TotalUserProjectDto();
        List<UserProject> list = getByUserId(userService.getCurrentUser().getId());
        List<Project> all = new ArrayList<>();
        List<Project> managing = new ArrayList<>();
        List<Project> member = new ArrayList<>();
        List<Project> closed = new ArrayList<>();
        list.forEach(userProject -> {
            Project project = userProject.getProject();
            all.add(project);
            if (userProject.getRole().equals(ProjectRole.ROLE_MANAGER)) {
                managing.add(project);
            } else {
                member.add(project);
            }
            if (project.isClosed()) {
                closed.add(project);
            }
        });
        result.setAll(all);
        result.setManaging(managing);
        result.setMember(member);
        result.setClosed(closed);
        return result;
    }

    public UserProjectDto updateUserProject(UserProjectDto dto) {
        return UserProjectDto.valueOf(userProjectRepository.save(dto.toEntity()));
    }

    public Long countProjectMember(Long projectId) {
        return userProjectRepository.countByProjectId(projectId);
    }
}

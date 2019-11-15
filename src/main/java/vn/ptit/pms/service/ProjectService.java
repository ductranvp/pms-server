package vn.ptit.pms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.UserProject;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.domain.enumeration.TaskStatus;
import vn.ptit.pms.domain.key.UserProjectKey;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.ProjectRepository;
import vn.ptit.pms.repository.UserProjectRepository;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.service.dto.CategoryTaskDto;
import vn.ptit.pms.service.dto.ProjectTaskDto;
import vn.ptit.pms.service.dto.TaskDto;
import vn.ptit.pms.service.dto.TaskFilterDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserProjectService userProjectService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    ProjectLogService projectLogService;

    public Project save(Project project, UserPrincipal userPrincipal) {
        project.setVerifyTask(true);
        Project savedProject = projectRepository.save(project);
        /* Save log */
        projectLogService.saveLog(savedProject.getId(), userPrincipal.getId());

        UserProject userProject = new UserProject(new UserProjectKey(userPrincipal.getId(), savedProject.getId()));
        userProject.setRole(ProjectRole.ROLE_MANAGER);
        userProjectService.save(userProject);
        return savedProject;
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

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project getOneById(Long id) {
        try {
            return projectRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new AppException("Project could not be found");
        }
    }

    public ProjectTaskDto getProjectTask(Long projectId, boolean isCategoryArchived, TaskFilterDto dto, UserPrincipal userPrincipal) {
        /* Save log */
        projectLogService.saveLog(projectId, userPrincipal.getId());

        ProjectTaskDto result = new ProjectTaskDto();
        result.setInfo(projectRepository.findById(projectId).get());

        List<Category> categories = categoryService.getByProjectId(projectId, isCategoryArchived);
        List<CategoryTaskDto> categoryTaskDtos = new ArrayList<>();
        categories.forEach(category -> {
            CategoryTaskDto categoryTaskDto = new CategoryTaskDto();
            categoryTaskDto.setInfo(category);
            List<TaskDto> tasks = taskService.getDtoByCategoryIdWithFilter(category.getId(), dto);
            categoryTaskDto.setNoProgress(tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.NO_PROGRESS)).collect(Collectors.toList()));
            categoryTaskDto.setInProgress(tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS)).collect(Collectors.toList()));
            categoryTaskDto.setCompleted(tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.COMPLETED)).collect(Collectors.toList()));
            categoryTaskDto.setVerified(tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.VERIFIED)).collect(Collectors.toList()));
            categoryTaskDto.setShow(true);
            categoryTaskDtos.add(categoryTaskDto);
        });
        result.setCategories(categoryTaskDtos);

        return result;
    }

    public boolean checkProjectAdmin(Long projectId, Long userId) {
        return userProjectRepository.findByProjectIdAndUserIdAndRole(projectId, userId, ProjectRole.ROLE_MANAGER) != null;
    }

    public void turnOnVerification(Long projectId) {
        Project project = projectRepository.getOne(projectId);
        project.setVerifyTask(true);
        projectRepository.save(project);
    }

    public void turnOffVerification(Long projectId) {
        Project project = projectRepository.getOne(projectId);
        project.setVerifyTask(false);
        projectRepository.save(project);
    }
}

package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.TaskRepository;
import vn.ptit.pms.repository.UserTaskRepository;
import vn.ptit.pms.service.dto.ActivityDto;
import vn.ptit.pms.service.dto.TaskDto;
import vn.ptit.pms.service.dto.UserDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final String ENTITY_NAME = "Task";
    private final int INCREMENT = (int) Math.pow(2, 16);
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ActivityService activityService;
    @Autowired
    UserService userService;
    @Autowired
    UserTaskRepository userTaskRepository;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserProjectService userProjectService;

    public Task create(Task task) {
        Long categoryId = task.getCategoryId();
        Integer lastPos = taskRepository.getLastTaskPos(categoryId);
        int pos = lastPos != null ? lastPos + INCREMENT : INCREMENT - 1;
        task.setPos(pos);
        Task savedTask = taskRepository.save(task);
        activityService.save(ActivityDto.created(savedTask.getId()));
        return savedTask;
    }

    @Transactional
    public void update(TaskDto dto, List<MultipartFile> files) {
        for (Attachment att : dto.getRemoveAttachments()) {
            attachmentService.delete(att.getId());
        }
        for (MultipartFile file : files) {
            attachmentService.save(null, dto.getId(), null, file);
        }

        Task oldTask = taskRepository.findById(dto.getId()).get();
        if (!oldTask.getPriority().equals(dto.getPriority())) {
            activityService.save(ActivityDto.changePriority(dto.getId(), dto.getPriority().name()));
        }
        taskRepository.save(dto.updateAttribute(oldTask));
    }

    @Transactional
    public void updateListPosition(List<Task> tasks) {
        int pos = INCREMENT - 1;
        for (int i = 0; i < tasks.size(); i++) {
            taskRepository.updatePos(tasks.get(i).getId(), pos);
            pos += INCREMENT;
        }
    }

    public Long getProjectIdByTaskId(Long taskId) {
        return taskRepository.findProjectIdByTaskId(taskId);
    }

    public TaskDto getDtoById(Long taskId) {
        Task currentTask = taskRepository.findById(taskId).get();
        TaskDto dto = new TaskDto(currentTask);
        dto.setUsers(userTaskRepository.findUserByTaskId(taskId));
        dto.setCreator(new UserDto(userService.getUserById(currentTask.getCreatedBy())));
        dto.setAttachments(attachmentService.getByTaskId(taskId));
        Category category = categoryService.getOneById(currentTask.getCategoryId());
        List<UserDto> userDtos = userProjectService.getListUserOfProject(category.getProjectId());
        dto.getUsers().forEach(userDtos::remove);
        dto.setUnassignedUsers(userDtos);
        return dto;
    }

    public List<TaskDto> getDtoByCategoryId(Long categoryId) {
        List<Task> tasks = taskRepository.findByCategoryIdOrderByPosAsc(categoryId);
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto dto = new TaskDto(task);
            dto.setUsers(userTaskRepository.findUserByTaskId(task.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public void updatePosition(Task task) {
        Task oldTask = taskRepository.findById(task.getId()).get();
        if (!oldTask.getStatus().equals(task.getStatus())) {
            activityService.save(ActivityDto.moveTask(task.getId(), task.getStatus().name()));
        }
        oldTask.setPos(task.getPos());
        oldTask.setStatus(task.getStatus());
        taskRepository.save(oldTask);
    }

    public void delete(Long id) {
        try {
            Task task = taskRepository.findById(id).get();
            task.setDeleted(true);
            taskRepository.save(task);
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }
}

package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Task create(Task task) {
        Long categoryId = task.getCategoryId();
        Integer lastPos = taskRepository.getLastTaskPos(categoryId);
        int pos = lastPos != null ? lastPos + INCREMENT : INCREMENT - 1;
        task.setPos(pos);
        Task savedTask = taskRepository.save(task);
        activityService.save(ActivityDto.created(savedTask.getId()));
        return savedTask;
    }

    public Task update(Task task) {
        Task oldTask = taskRepository.findById(task.getId()).get();
        if (!oldTask.getStatus().equals(task.getStatus())) {
            activityService.save(ActivityDto.moveTask(task.getId(), task.getStatus().name()));
        } else if (!oldTask.getPriority().equals(task.getPriority())) {
            activityService.save(ActivityDto.changePriority(task.getId(), task.getPriority().name()));
        }
        return taskRepository.save(task);
    }

    @Transactional
    public void updateListPosition(List<Task> tasks){
        int pos = INCREMENT - 1;
        taskRepository.updatePos(tasks.get(0).getId(), pos);
        for (int i = 1; i < tasks.size(); i++){
            pos += INCREMENT;
            taskRepository.updatePos(tasks.get(i).getId(), pos);
        }
    }

    public void updateList(List<Task> list) {
        taskRepository.saveAll(list);
    }

    public Task getById(Long id) {
        try {
            return taskRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public TaskDto getDtoById(Long id) {
        Task currentTask = taskRepository.findById(id).get();
        TaskDto dto = new TaskDto(currentTask);
        dto.setUsers(userTaskRepository.findUserByTaskId(id));
        dto.setCreator(new UserDto(userService.getUserById(currentTask.getCreatedBy())));
        dto.setAttachments(attachmentService.getByTaskId(id));
        return dto;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
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

    public List<Task> getByCategoryId(Long categoryId) {
        return taskRepository.findByCategoryIdOrderByPosAsc(categoryId);
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
}

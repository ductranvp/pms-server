package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.TaskRepository;
import vn.ptit.pms.service.dto.ActivityDto;

import java.util.List;

@Service
public class TaskService {
    private final String ENTITY_NAME = "Task";

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    private final int INCREMENT = (int) Math.pow(2,16);

    public Task create(Task task) {
        Long categoryId = task.getCategoryId();
        Integer lastPos = taskRepository.getLastTaskPos(categoryId);
        int pos = lastPos != null ? lastPos + INCREMENT : INCREMENT-1;
        task.setPos(pos);
        Task savedTask = taskRepository.save(task);
        activityService.save(ActivityDto.created(userService.getCurrentUserFullName(), savedTask.getId()));
        return savedTask;
    }

    public Task update(Task task) {
        Task oldTask = taskRepository.findById(task.getId()).get();
        if (!oldTask.getStatus().equals(task.getStatus())) {
            String actor = userService.getCurrentUserFullName();
            activityService.save(ActivityDto.moveTask(actor, task.getStatus().name(), task.getId()));
        } else if (!oldTask.getPriority().equals(task.getPriority())) {
            String actor = userService.getCurrentUserFullName();
            activityService.save(ActivityDto.changePriority(actor, task.getPriority().name(), task.getId()));
        }
        return taskRepository.save(task);
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

    public List<Task> getByCategoryId(Long categoryId){
        return taskRepository.findByCategoryIdOrderByPosAsc(categoryId);
    }
}

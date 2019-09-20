package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final String ENTITY_NAME = "Task";

    @Autowired
    TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findOne(Long id) {
        try {
            return taskRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ENTITY_NAME + " " + id + " could not be found");
        }
    }

    public List<Task> findAll() {
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
}

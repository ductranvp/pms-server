package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Task;
import vn.ptit.qldaserver.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findOne(Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}

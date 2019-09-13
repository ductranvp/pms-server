package vn.ptit.qldaserver.service.impl;

import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Task;
import vn.ptit.qldaserver.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Task findOne(Long id) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

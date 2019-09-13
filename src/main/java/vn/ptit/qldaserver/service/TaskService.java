package vn.ptit.qldaserver.service;

import vn.ptit.qldaserver.domain.Task;

import java.util.List;

public interface TaskService {
    Task save(Task task);
    Task findOne(Long id);
    List<Task> findAll();
    void delete(Long id);
}

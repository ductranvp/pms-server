package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserTask;
import vn.ptit.pms.domain.key.UserTaskKey;
import vn.ptit.pms.repository.UserTaskRepository;
import vn.ptit.pms.service.dto.AssignTaskDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTaskService {
    @Autowired
    UserTaskRepository userTaskRepository;

    public void save(AssignTaskDto dto) {
        for (Long userId : dto.getListUserId()) {
            UserTask userTask = new UserTask(new UserTaskKey(userId, dto.getTaskId()));
            userTaskRepository.save(userTask);
        }
    }

    public void delete(AssignTaskDto dto) {
        for (Long userId : dto.getListUserId()) {
            UserTask userTask = new UserTask(new UserTaskKey(userId, dto.getTaskId()));
            userTaskRepository.delete(userTask);
        }
    }

    public List<Task> getListTaskOfUser(Long userId) {
        List<Task> result = new ArrayList<>();
        List<UserTask> userTasks = userTaskRepository.findByIdUserId(userId);
        for (UserTask userTask : userTasks) {
            result.add(userTask.getTask());
        }
        return result;
    }

    public List<User> getListUserOfTask(Long taskId) {
        List<User> result = new ArrayList<>();
        List<UserTask> userTasks = userTaskRepository.findByIdTaskId(taskId);
        for (UserTask userTask : userTasks) {
            result.add(userTask.getUser());
        }
        return result;
    }
}

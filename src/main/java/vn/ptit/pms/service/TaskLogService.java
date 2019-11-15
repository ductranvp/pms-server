package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.TaskLog;
import vn.ptit.pms.repository.TaskLogRepository;
import vn.ptit.pms.service.dto.TaskDrawerDto;
import vn.ptit.pms.service.dto.TaskDto;
import vn.ptit.pms.service.dto.TaskLogDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskLogService {
    @Autowired
    TaskLogRepository taskLogRepository;
    @Autowired
    TaskService taskService;

    @PersistenceContext
    EntityManager em;

    public void saveLog(Long taskId, Long userId) {
        taskLogRepository.save(new TaskLog(taskId, userId));
    }

    public List<TaskLogDto> getRecentTask(Long userId) {
        String sql = "SELECT DISTINCT t.* FROM task_log tl " +
                "JOIN task t ON t.id = tl.task_id " +
                "WHERE tl.user_id = " + userId + " " +
                "ORDER BY tl.id DESC LIMIT 6";

        Query query = em.createNativeQuery(sql, Task.class);

        List<Task> list = query.getResultList();
//        List<Task> list = taskLogRepository.findRecentTask(userId);
        return list.stream().map(task -> {
            TaskDrawerDto drawerDto = taskService.getTaskDrawer(task.getId(), userId);
            TaskDto taskDto = taskService.getDtoById(task.getId(), userId, true);
            TaskLogDto logDto = new TaskLogDto(drawerDto);
            logDto.setTaskDto(taskDto);
            return logDto;
        }).collect(Collectors.toList());
    }
}

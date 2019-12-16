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
import vn.ptit.pms.service.dto.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    UserTaskService userTaskService;

    @Autowired
    TaskLogService taskLogService;

    @PersistenceContext
    EntityManager em;

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
    public void update(TaskDto dto, boolean isTaskReport, List<MultipartFile> files) {
        Long taskId = dto.getId();
        /* Attachments */
        for (Attachment att : dto.getRemoveAttachments()) {
            attachmentService.delete(att.getId());
        }

//        for (AttachmentDto att : dto.getRemoveReports()) {
//            attachmentService.delete(att.getId());
//        }

        for (MultipartFile file : files) {
            attachmentService.save(null, taskId, null, isTaskReport, null, file);
        }
        /* Assign */
        if (isTaskReport) return;
        userTaskService.delete(new AssignTaskDto(taskId, dto.getRemoveAssignUserIds()));
        userTaskService.save(new AssignTaskDto(taskId, dto.getAssignUserIds()));

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

    public TaskDto getDtoById(Long taskId, Long userId, boolean isLoadLog) {
        /*Save log*/
        if (!isLoadLog)
            taskLogService.saveLog(taskId, userId);

        Task currentTask = taskRepository.findById(taskId).get();
        TaskDto dto = new TaskDto(currentTask);
        dto.setAssignedUsers(userTaskRepository.findUserByTaskId(taskId));
        dto.setCreator(new UserDto(userService.getUserById(currentTask.getCreatedBy())));
        dto.setAttachments(attachmentService.getByTaskId(taskId));
        Category category = categoryService.getOneById(currentTask.getCategoryId());
        List<UserDto> userDtos = userProjectService.getListUserOfProject(category.getProjectId());
        dto.getAssignedUsers().forEach(userDtos::remove);
        dto.setUnassignedUsers(userDtos);
        dto.setReports(attachmentService.getTaskReport(taskId));
        return dto;
    }

    public List<Task> getTaskByFilter(Long categoryId, TaskFilterDto dto) {
        String priorityFilter = "";
        String userIdFilter = "";
        for (String priority : dto.getPriorities()) {
            priorityFilter += "'" + priority + "',";
        }

        for (Long userId : dto.getUserIds()) {
            userIdFilter += userId + ",";
        }

        String noFilter = "SELECT DISTINCT t.* FROM task t " +
                "WHERE t.deleted = FALSE " +
                "AND t.category_id = " + categoryId;

        String hasFilter = "SELECT DISTINCT t.* FROM task t " +
                "LEFT JOIN user_task ut on t.id = ut.task_id " +
                "WHERE t.deleted = FALSE " +
                "AND t.category_id = " + categoryId;

//        if (dto.getOverdue() != null) {
//            noFilter += " AND t.overdue = " + dto.getOverdue();
//            hasFilter += " AND t.overdue = " + dto.getOverdue();
//        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (dto.getStartDate() != null) {
            Date myDate = Date.from(dto.getStartDate());
            String formattedDate = formatter.format(myDate);
            noFilter += " AND t.estimate_start_date >= '" + formattedDate + "'";
            hasFilter += " AND t.estimate_start_date >= '" + formattedDate + "'";
        }

        if (dto.getEndDate() != null) {
            Date myDate = Date.from(dto.getEndDate());
            String formattedDate = formatter.format(myDate);
            noFilter += " AND t.estimate_end_date <= '" + formattedDate + "'";
            hasFilter += " AND t.estimate_end_date <= '" + formattedDate + "'";
        }


        if (dto.getSearchText() != null) {
            noFilter += " AND t.name LIKE '%" + dto.getSearchText() + "%'";
        }

        Query query;
        if (dto.getPriorities().isEmpty() && dto.getUserIds().isEmpty()) {
            noFilter += " ORDER BY t.pos ASC";
            query = em.createNativeQuery(noFilter, Task.class);
        } else {
            if (dto.getPriorities().isEmpty()) {
                userIdFilter = userIdFilter.substring(0, userIdFilter.length() - 1);
                hasFilter += " AND ut.user_id IN (" + userIdFilter + ")";
                hasFilter += " ORDER BY t.pos ASC";
                query = em.createNativeQuery(hasFilter, Task.class);
            } else if (dto.getUserIds().isEmpty()) {
                priorityFilter = priorityFilter.substring(0, priorityFilter.length() - 1);
                hasFilter += " AND t.priority IN (" + priorityFilter + ")";
                hasFilter += " ORDER BY t.pos ASC";
                query = em.createNativeQuery(hasFilter, Task.class);
            } else {
                userIdFilter = userIdFilter.substring(0, userIdFilter.length() - 1);
                priorityFilter = priorityFilter.substring(0, priorityFilter.length() - 1);

                hasFilter += " AND ut.user_id IN (" + userIdFilter + ")";
                hasFilter += " AND t.priority IN (" + priorityFilter + ")";
                hasFilter += " ORDER BY t.pos ASC";
                query = em.createNativeQuery(hasFilter, Task.class);
            }
        }
        List<Task> result = query.getResultList();
        return result;
    }

    public List<TaskDto> getDtoByCategoryIdWithFilter(Long categoryId, TaskFilterDto filter) {
        List<Task> tasks = getTaskByFilter(categoryId, filter);
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto dto = new TaskDto(task);
            dto.setAssignedUsers(userTaskRepository.findUserByTaskId(task.getId()));
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

    public void updateProgress(Long taskId, long progress) {
        Task task = taskRepository.findById(taskId).get();
        activityService.save(ActivityDto.updateProgress(task.getId(), progress));
        task.setProgress(progress);
        taskRepository.save(task);
    }

    public TaskDrawerDto getTaskDrawer(Long taskId, Long userId) {
        Task task = taskRepository.getOne(taskId);
        TaskDrawerDto dto = new TaskDrawerDto();
        Category category = categoryService.getOneById(task.getCategoryId());
        dto.setArchived(category.isArchived());
        dto.setTaskId(taskId);
        dto.setProjectId(category.getProjectId());
        dto.setManager(projectService.checkProjectAdmin(category.getProjectId(), userId));
        return dto;
    }
}

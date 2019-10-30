package vn.ptit.pms.service.dto;

import lombok.*;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.Task;
import vn.ptit.pms.domain.enumeration.TaskPriority;
import vn.ptit.pms.domain.enumeration.TaskStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private int pos;
    private TaskPriority priority = TaskPriority.NONE;
    private long progress;
    private Instant estimateStartDate;
    private Instant estimateEndDate;
    private Instant startDate;
    private Instant endDate;
    private TaskStatus status = TaskStatus.NO_PROGRESS;
    private boolean overdue = false;
    private Long categoryId;
    private List<UserDto> users = new ArrayList<>();
    private UserDto creator;
    private List<Attachment> attachments = new ArrayList<>();

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.pos = task.getPos();
        this.priority = task.getPriority();
        this.progress = task.getProgress();
        this.estimateStartDate = task.getEstimateStartDate();
        this.estimateEndDate = task.getEstimateEndDate();
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.status = task.getStatus();
        this.overdue = task.isOverdue();
        this.categoryId = task.getCategoryId();
    }
}

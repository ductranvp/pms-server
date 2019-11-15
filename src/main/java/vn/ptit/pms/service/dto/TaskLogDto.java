package vn.ptit.pms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskLogDto {
    private boolean isArchived;
    private Long projectId;
    private Long taskId;
    private boolean isManager;
    private TaskDto taskDto;

    public TaskLogDto(TaskDrawerDto drawerDto) {
        this.isArchived = drawerDto.isArchived();
        this.projectId = drawerDto.getProjectId();
        this.taskId = drawerDto.getTaskId();
        this.isManager = drawerDto.isManager();
    }
}

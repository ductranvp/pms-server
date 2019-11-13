package vn.ptit.pms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDrawerDto {
    private boolean isArchived;
    private Long projectId;
    private Long taskId;
    private boolean isManager;
}

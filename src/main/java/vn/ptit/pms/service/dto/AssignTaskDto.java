package vn.ptit.pms.service.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AssignTaskDto {
    private Long taskId;
    private List<Long> listUserId;
}

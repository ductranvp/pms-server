package vn.ptit.pms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilterDto {
    private Long projectId;
    private List<String> priorities = new ArrayList<>();
    private List<Long> userIds = new ArrayList<>();
    private String searchText;
    private Instant startDate;
    private Instant endDate;
    private Boolean overdue;
}

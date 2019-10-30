package vn.ptit.pms.service.dto;

import lombok.Data;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.domain.Task;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryTaskDto {
    private boolean show = true;
    private Category info;
    private List<TaskDto> noProgress = new ArrayList<>();
    private List<TaskDto> inProgress = new ArrayList<>();
    private List<TaskDto> completed = new ArrayList<>();
    private List<TaskDto> verified = new ArrayList<>();
}

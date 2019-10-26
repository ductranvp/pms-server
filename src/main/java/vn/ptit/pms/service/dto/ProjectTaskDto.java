package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectTaskDto {
    private Project info;
    private List<CategoryTaskDto> categories = new ArrayList<>();

    public Project getInfo() {
        return info;
    }

    public void setInfo(Project info) {
        this.info = info;
    }

    public List<CategoryTaskDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryTaskDto> categories) {
        this.categories = categories;
    }
}

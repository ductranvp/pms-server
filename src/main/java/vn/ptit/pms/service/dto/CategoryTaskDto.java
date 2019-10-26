package vn.ptit.pms.service.dto;

import vn.ptit.pms.domain.Category;
import vn.ptit.pms.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class CategoryTaskDto {
    private boolean show = true;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private Category info;
    private List<Task> noProgress = new ArrayList<>();
    private List<Task> inProgress = new ArrayList<>();
    private List<Task> completed = new ArrayList<>();
    private List<Task> verified = new ArrayList<>();

    public Category getInfo() {
        return info;
    }

    public void setInfo(Category info) {
        this.info = info;
    }

    public List<Task> getNoProgress() {
        return noProgress;
    }

    public void setNoProgress(List<Task> noProgress) {
        this.noProgress = noProgress;
    }

    public List<Task> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<Task> inProgress) {
        this.inProgress = inProgress;
    }

    public List<Task> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Task> completed) {
        this.completed = completed;
    }

    public List<Task> getVerified() {
        return verified;
    }

    public void setVerified(List<Task> verified) {
        this.verified = verified;
    }
}

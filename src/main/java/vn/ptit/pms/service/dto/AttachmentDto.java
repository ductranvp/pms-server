package vn.ptit.pms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.Attachment;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {
    private Long id;
    private String name;
    private String url;
    private String type;
    private Long projectId;
    private Long taskId;
    private Long commentId;
    private UserDto createdBy;
    private Instant createdDate;

    public AttachmentDto(Attachment attachment) {
        this.id = attachment.getId();
        this.name = attachment.getName();
        this.url = attachment.getUrl();
        this.type = attachment.getType();
        this.projectId = attachment.getProjectId();
        this.taskId = attachment.getTaskId();
        this.commentId = attachment.getCommentId();
        this.createdDate = attachment.getCreatedDate();
    }
}

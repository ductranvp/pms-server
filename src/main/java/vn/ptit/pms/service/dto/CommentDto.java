package vn.ptit.pms.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentDto {
    private Long id;
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean edited = false;
    private Long taskId;
    private List<Attachment> removeAttachments = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Attachment> attachments = new ArrayList<>();

    public static CommentDto valueOf(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setEdited(comment.isEdited());
        dto.setTaskId(comment.getTaskId());
        return dto;
    }

    public Comment convertToEntity() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(content);
        comment.setEdited(edited);
        comment.setTaskId(taskId);
        return comment;
    }

    public void updateAttrFromEntity(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        edited = comment.isEdited();
        taskId = comment.getTaskId();
    }

    public Comment updateAttrToEntity(Comment comment) {
        comment.setId(id);
        comment.setContent(content);
        comment.setEdited(edited);
        comment.setTaskId(taskId);
        return comment;
    }
}

package vn.ptit.pms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.enumeration.AttachmentType;

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
    private String description;
    private AttachmentType targetType;
    private Long targetId;
    private UserDto createdBy;
    private Instant createdDate;

    public AttachmentDto(Attachment attachment) {
        this.id = attachment.getId();
        this.name = attachment.getName();
        this.url = attachment.getUrl();
        this.type = attachment.getType();
        this.description = attachment.getDescription();
        this.targetId = attachment.getTargetId();
        this.targetType = attachment.getTargetType();
        this.createdDate = attachment.getCreatedDate();
    }
}

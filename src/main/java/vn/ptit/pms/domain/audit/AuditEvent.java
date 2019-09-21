package vn.ptit.pms.domain.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @JsonIgnore
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @CreatedDate
    @JsonIgnore
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "last_modified_by", length = 50)
    private Long lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();
}

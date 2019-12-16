package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Attachment;
import vn.ptit.pms.domain.enumeration.AttachmentType;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTargetTypeAndTargetId(AttachmentType targetType, Long targetId);
}

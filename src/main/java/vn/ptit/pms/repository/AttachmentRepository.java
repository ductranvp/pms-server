package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}

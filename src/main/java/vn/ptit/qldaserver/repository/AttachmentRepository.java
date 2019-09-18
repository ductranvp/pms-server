package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}

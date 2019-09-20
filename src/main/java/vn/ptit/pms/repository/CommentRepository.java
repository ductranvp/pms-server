package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

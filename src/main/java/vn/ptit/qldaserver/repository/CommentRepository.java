package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

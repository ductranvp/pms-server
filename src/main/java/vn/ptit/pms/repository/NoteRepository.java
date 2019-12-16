package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByCreatedByOrderByCreatedDateDesc(Long userId);
}

package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Note;
import vn.ptit.pms.repository.NoteRepository;
import vn.ptit.pms.security.UserPrincipal;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Transactional
    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note getOneById(Long id) {
        return noteRepository.findById(id).get();
    }

    public Note update(Note note) {
        return noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> getAll(UserPrincipal userPrincipal) {
        return noteRepository.findByCreatedByOrderByCreatedDateDesc(userPrincipal.getId());
    }
}

package vn.ptit.pms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Note;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/api/note/")
public class NoteResource {
    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.create(note));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Note> getOneById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getOneById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAll(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(noteService.getAll(userPrincipal));
    }

    @PutMapping("/update")
    public ResponseEntity<Note> update(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.update(note));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.ok().build();
    }
}

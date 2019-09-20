package vn.ptit.pms.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserTaskResource {
    @PostMapping("/user-task")
    public ResponseEntity<?> assignTask(){
        return null;
    }
}

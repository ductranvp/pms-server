package vn.ptit.qldaserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.AuthoritiesConstants;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserResource {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        User user = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(user);
    }


}

package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.security.AuthoritiesConstants;
import vn.ptit.pms.service.MailService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.UserDto;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @PostMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        log.debug("REST request to save User : {}", userDto);

        if (userDto.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new user cannot already have an ID"), HttpStatus.BAD_REQUEST);
        } else if (userRepository.findByUsername(userDto.getUsername().toLowerCase()).isPresent()) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Username has already existed"), HttpStatus.BAD_REQUEST);
        } else if (userRepository.findByEmail(userDto.getEmail().toLowerCase()).isPresent()) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Email has already existed"), HttpStatus.BAD_REQUEST);
        } else {
            User newUser = userService.createUser(userDto);
            mailService.sendEmailFromTemplate(newUser, "passwordResetEmail", "email.reset.title");
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }

    @PutMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        log.debug("REST request to update User : {}", userDto);
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Email has already existed"), HttpStatus.BAD_REQUEST);
        }
        existingUser = userRepository.findByUsername(userDto.getUsername().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Username has already existed"), HttpStatus.BAD_REQUEST);
        }
        User updatedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{username}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(ErrorEntity.notFound("Username could not be found!"),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @GetMapping("/users/authorities")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @DeleteMapping("/users/{username}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

}

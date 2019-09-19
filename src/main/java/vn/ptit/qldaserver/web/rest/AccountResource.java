package vn.ptit.qldaserver.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.JwtTokenProvider;
import vn.ptit.qldaserver.security.SecurityUtils;
import vn.ptit.qldaserver.service.MailService;
import vn.ptit.qldaserver.service.UserService;
import vn.ptit.qldaserver.service.dto.UserDto;
import vn.ptit.qldaserver.service.dto.core.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountResource {
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @PostMapping("account/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpDto signUpDto) {
        log.info("REST request to register Account: {}", signUpDto.getUsername());
        if (userRepository.existsByUsername(signUpDto.getUsername().toLowerCase())) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Username is already taken"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail().toLowerCase())) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Email address already in use"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(signUpDto);
        log.info("User is created. Sending activation key to email: {}", user.getEmail());
        mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
        return ResponseEntity.ok().build();
    }

    @PostMapping("account/activate")
    public ResponseEntity<String> activateAccount(@RequestBody String key) {
        log.info("REST request to activate Account with key: {}", key);
        return userService.activateRegistration(key)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("account/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDto loginDto) {
        log.info("REST request to authenticate for Account: {}", loginDto.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtTokenDto(jwt));
    }

    @GetMapping("/account/current")
    public ResponseEntity<UserDto> getCurrentAccount() {
        log.info("REST request to get current Account");
        return Optional.ofNullable(userService.getCurrentUser())
                .map(user -> new ResponseEntity<>(new UserDto(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/account/update")
    public ResponseEntity<?> updateAccount(@RequestBody UserDto userDto) {
        log.info("REST request to update current Account");
        final String username = SecurityUtils.getCurrentUserLogin();
        if (username == null)
            return new ResponseEntity<>(ErrorEntity.notFound("Current user could not be found"), HttpStatus.NOT_FOUND);
        Optional<User> existingUser = userRepository.findOneByEmail(userDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(username))) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Email has already existed"), HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findOneByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(ErrorEntity.notFound("User could not be found"), HttpStatus.NOT_FOUND);
        }
        userService.updateUser(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                userDto.getLangKey(), userDto.getImageUrl());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/account/change_password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        log.info("REST request to change password for Account: {}", SecurityUtils.getCurrentUserLogin());
        Optional<User> user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.get().getPassword())) {
                User entity = user.get();
                entity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                userRepository.save(entity);
                return ResponseEntity.ok().build();
            } else {
                return new ResponseEntity<>(ErrorEntity.badRequest("Old password not matches"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(ErrorEntity.notFound("Current user could not be found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/account/reset_password/request")
    public ResponseEntity<?> requestResetPassword(@RequestParam(value = "email") String email) {
        return userService.requestPasswordReset(email)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/account/reset_password/finish")
    public ResponseEntity<?> finishResetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.completePasswordReset(resetPasswordDto.getNewPassword(), resetPasswordDto.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

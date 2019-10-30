package vn.ptit.pms.web.rest;


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
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.repository.AuthorityRepository;
import vn.ptit.pms.repository.UserRepository;
import vn.ptit.pms.security.JwtTokenProvider;
import vn.ptit.pms.security.SecurityUtils;
import vn.ptit.pms.service.MailService;
import vn.ptit.pms.service.UserService;
import vn.ptit.pms.service.dto.UserDto;
import vn.ptit.pms.service.dto.core.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountResource {
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final String ENTITY_NAME = "Account";
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

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpDto signUpDto) {
        log.info("REST request to register {}: {}", ENTITY_NAME, signUpDto.getUsername());
        if (userRepository.existsByUsername(signUpDto.getUsername().toLowerCase())) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Username is already taken"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail().toLowerCase())) {
            return new ResponseEntity<>(ErrorEntity.badRequest("Email address already in use"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(signUpDto);
        log.info("{} is created. Sending activation key to email: {}", ENTITY_NAME, user.getEmail());
        mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestBody String key) {
        log.info("REST request to activate {} with key: {}", ENTITY_NAME, key);
        return userService.activateRegistration(key)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDto loginDto) {
        log.info("REST request to authenticate for {}: {}", ENTITY_NAME, loginDto.getUsername());
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

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentAccount() {
        log.info("REST request to get current {}", ENTITY_NAME);
        return Optional.ofNullable(userService.getCurrentUser())
                .map(user -> new ResponseEntity<>(new UserDto(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/update")
    public ResponseEntity updateAccount(@RequestPart("dto") UserDto userDto, @RequestPart(value = "image", required = false) MultipartFile image) {
        log.info("REST request to update current {}", ENTITY_NAME);
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
        userService.updateUser(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), image);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/change_password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        log.info("REST request to change password for {}: {}", ENTITY_NAME, SecurityUtils.getCurrentUserLogin());
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

    @PostMapping("/reset_password/request")
    public ResponseEntity requestResetPassword(@RequestBody String email) {
        return userService.requestPasswordReset(email)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/reset_password/finish")
    public ResponseEntity finishResetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.completePasswordReset(resetPasswordDto.getNewPassword(), resetPasswordDto.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

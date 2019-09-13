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
import vn.ptit.qldaserver.service.dto.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDto loginDto) {
        log.info("Authenticating for account: {}", loginDto.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtTokenDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername().toLowerCase())) {
            return new ResponseEntity<>(ApiError.badRequest("Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail().toLowerCase())) {
            return new ResponseEntity<>(ApiError.badRequest("Email address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(signUpDto);
        log.info("Sending activation key to email: {}", user.getEmail());
        mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/current")
    public ResponseEntity<AccountDto> getCurrentAccount() {
        return Optional.ofNullable(userService.getCurrentUser())
                .map(user -> new ResponseEntity<>(new AccountDto(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDto accountDto) {
        final String username = SecurityUtils.getCurrentUsername();
        log.info("Request to update account: {}", username);
        Optional<User> existingUser = userRepository.findOneByEmail(accountDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(username))) {
            return new ResponseEntity<>(ApiError.badRequest("Update user failed"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findOneByUsername(username).isPresent()) {
            userService.updateUser(accountDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        Optional<User> user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        log.info("Request to change password for account: {}", SecurityUtils.getCurrentUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.get().getPassword())) {
                User entity = user.get();
                entity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                userRepository.save(entity);
                return ResponseEntity.ok().build();
            } else {
                return new ResponseEntity<>(ApiError.badRequest("Old password not matches"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(ApiError.notFound("Account not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestBody String key) {
        return userService.activateRegistration(key)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/activation_key")
    public ResponseEntity<?> resendActivationKey(@RequestParam(value = "email") String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isActivated()) {
                return new ResponseEntity<>(ApiError.badRequest("Your account has been already activated"), HttpStatus.BAD_REQUEST);
            } else {
                log.info("Resending activation key to email: {}", email);
                mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
                return ResponseEntity.ok(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ApiError.notFound("Email not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reset_password/request")
    public ResponseEntity<?> requestResetPassword(@RequestParam(value = "email") String email) {
        return userService.requestPasswordReset(email)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/reset_password/finish")
    public ResponseEntity<?> finishResetPassword(@RequestBody KeyAndPasswordDto keyAndPassword) {
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/init")
    public void initAccount() {
        userService.initAccount();
    }
}

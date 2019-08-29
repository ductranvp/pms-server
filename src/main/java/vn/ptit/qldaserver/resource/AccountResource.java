package vn.ptit.qldaserver.resource;


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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.ptit.qldaserver.model.User;
import vn.ptit.qldaserver.payload.*;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.JwtTokenProvider;
import vn.ptit.qldaserver.service.MailService;
import vn.ptit.qldaserver.service.UserService;
import vn.ptit.qldaserver.util.RandomUtil;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Processing login request for account: {}", loginRequest.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtTokenResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(signUpRequest);
        mailService.sendActivationMail(user.getEmail(), user.getActivationKey());
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/resendActivationKey")
    public ResponseEntity<?> resendActivationMail(@RequestParam(value = "email") String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getActivationKey().isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "No activation key found"), HttpStatus.BAD_REQUEST);
            } else {
                log.info("Resend activation key for email: {}", email);
                mailService.sendActivationMail(user.getEmail(), user.getActivationKey());
                return ResponseEntity.ok(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new ApiResponse(false, "Email not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resetPassword/request")
    public ResponseEntity<?> resetPassword(@RequestParam(value = "email") String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setResetKey(RandomUtil.generateResetKey());
            userRepository.save(user);
            log.info("Sending reset password request to email: {}", email);
            mailService.sendResetPasswordMail(user.getEmail(), user.getResetKey());
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse(false, "Email not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resetPassword/finish")
    public ResponseEntity<?> finishResetPassword(@RequestBody KeyAndPasswordVM keyAndPassword) {
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

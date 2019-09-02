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
import vn.ptit.qldaserver.dto.*;
import vn.ptit.qldaserver.model.User;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.AuthoritiesConstants;
import vn.ptit.qldaserver.security.JwtTokenProvider;
import vn.ptit.qldaserver.security.SecurityUtils;
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("Authenticating for account: {}", loginRequestDto.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsernameOrEmail(),
                        loginRequestDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtTokenResponseDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername().toLowerCase())) {
            return new ResponseEntity<>(new ApiResponseDto(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail().toLowerCase())) {
            return new ResponseEntity<>(new ApiResponseDto(false, "Email address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.registerUser(signUpRequestDto);
        log.info("Sending activation key to email: {}", user.getEmail());
        mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "User registered successfully"));
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
        Optional<User> existingUser = userRepository.findOneByEmail(accountDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(username))) {
            return new ResponseEntity<>(new ApiResponseDto(false, "Update user failed", null), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findOneByUsername(username).isPresent()){
            userService.updateUser(accountDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword() {
        return null;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
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
                return new ResponseEntity<>(new ApiResponseDto(false, "Your account has been already activated"), HttpStatus.BAD_REQUEST);
            } else {
                log.info("Resending activation key to email: {}", email);
                mailService.sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
                return ResponseEntity.ok(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponseDto(false, "Email not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset_password/request")
    public ResponseEntity<?> requestResetPassword(@RequestParam(value = "email") String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setResetKey(RandomUtil.generateResetKey());
            userRepository.save(user);
            log.info("Sending password reset request to email: {}", email);
            mailService.sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponseDto(false, "Email not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset_password/finish")
    public ResponseEntity<?> finishResetPassword(@RequestBody KeyAndPasswordDto keyAndPassword) {
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/reset_password/check")
    public ResponseEntity<?> checkResetKey(@RequestParam(value = "key") String key) {
        Optional<User> optionalUser = userRepository.findOneByResetKey(key);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponseDto(true, "Reset key existed"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponseDto(false, "Reset key not found"), HttpStatus.BAD_REQUEST);
    }
}

package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Authority;
import vn.ptit.qldaserver.domain.User;
import vn.ptit.qldaserver.domain.enumeration.AuthorityName;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.ProjectRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.SecurityUtils;
import vn.ptit.qldaserver.service.dto.UserDto;
import vn.ptit.qldaserver.service.dto.core.SignUpDto;
import vn.ptit.qldaserver.util.CommonConstants;
import vn.ptit.qldaserver.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    public Optional<User> activateRegistration(String key) {
        log.info("Activating user for activation key {}", key);
        Optional<User> optionalUser = userRepository.findOneByActivationKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActivated(true);
            user.setActivationKey(null);
            userRepository.save(user);
        }
        return optionalUser;
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.info("Resetting user password for reset key {}", key);
        Optional<User> optionalUser = userRepository.findOneByResetKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            userRepository.save(user);
        }
        return optionalUser;
    }

    public Optional<User> requestPasswordReset(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setResetKey(RandomUtil.generateResetKey());
            userRepository.save(user);
            log.info("Sending password reset request to email: {}", email);
            mailService.sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
        }
        return optional;
    }

    public User registerUser(SignUpDto request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername().toLowerCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLangKey(request.getLangKey());
        user.setActivationKey(RandomUtil.generateActivationKey());
        Authority userRole = authorityRepository.findByName(AuthorityName.ROLE_USER)
                .orElseThrow(() -> new AppException("User authority not set."));
        user.setAuthorities(Collections.singleton(userRole));
        log.info("Created information for User: {}", user);
        return userRepository.save(user);
    }

    public User createUser(UserDto userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(CommonConstants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(authorityName -> {
                Authority authority = authorityRepository.findByName(AuthorityName.valueOf(authorityName)).get();
                authorities.add(authority);
            });
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        Optional<User> optionalUser = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email.toLowerCase());
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            log.debug("Changed Information for User: {}", user);
            userRepository.save(user);
        }
    }

    public User updateUser(UserDto userDto) {
        User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin()).get();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setLangKey(userDto.getLangKey());
        user.setActivated(userDto.isActivated());
        user.setImageUrl(userDto.getImageUrl());
        if (userDto.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDto.getAuthorities().forEach(authority ->
                    authorities.add(authorityRepository.findByName(AuthorityName.valueOf(authority)).get()));
            user.setAuthorities(authorities);
        }
        log.debug("Changed information for User: {}", user);
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        log.info("Querying current user info");
        return userRepository.findByUsername(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void initAccount() {
        if (userRepository.findByUsername("admin").isPresent() || userRepository.findByEmail("admin@gmail.com").isPresent()) {
            return;
        }
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@gmail.com");
        admin.setActivated(true);
        List<Authority> authorities = authorityRepository.findAll();
        Set<Authority> authoritySet = new HashSet<>(authorities);
        admin.setAuthorities(authoritySet);
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        userRepository.save(admin);
    }

    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(authority -> authority.getName().name()).collect(Collectors.toList());
    }
}

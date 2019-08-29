package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.model.Authority;
import vn.ptit.qldaserver.model.User;
import vn.ptit.qldaserver.model.enumeration.AuthorityName;
import vn.ptit.qldaserver.payload.SignUpRequest;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.util.RandomUtil;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    public User registerUser(SignUpRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivationKey(RandomUtil.generateActivationKey());
        Authority userRole = authorityRepository.findByName(AuthorityName.ROLE_USER)
                .orElseThrow(() -> new AppException("User authority not set."));
        user.setAuthorities(Collections.singleton(userRole));
        log.info("Created Information for User: {}", user);
        return userRepository.save(user);
    }

    public Optional<User> activateRegistration(String key) {
        log.info("Activating user for activation key {}", key);
        Optional<User> optionalUser = userRepository.findOneByActivationKey(key);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setActivated(true);
            user.setActivationKey(null);
            userRepository.save(user);
        }
        return optionalUser;
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.info("Reset user password for reset key {}", key);
        Optional<User> optionalUser = userRepository.findOneByResetKey(key);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            userRepository.save(user);
        }
        return optionalUser;
    }
}

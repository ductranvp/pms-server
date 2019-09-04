package vn.ptit.qldaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.dto.AccountDto;
import vn.ptit.qldaserver.dto.SignUpRequestDto;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.model.Authority;
import vn.ptit.qldaserver.model.User;
import vn.ptit.qldaserver.model.enumeration.AuthorityName;
import vn.ptit.qldaserver.repository.AuthorityRepository;
import vn.ptit.qldaserver.repository.UserRepository;
import vn.ptit.qldaserver.security.SecurityUtils;
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

    public User registerUser(SignUpRequestDto request) {
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

    public User getCurrentUser() {
        log.info("Querying current user info");
        return userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
    }

    public void updateUser(AccountDto accountDto) {
        User user = userRepository.findOneByUsername(SecurityUtils.getCurrentUsername()).get();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setEmail(accountDto.getEmail());
        user.setLangKey(accountDto.getLangKey());
        user.setImageUrl(accountDto.getImageUrl());
        log.debug("Changed information for User: {}", user);
        userRepository.save(user);
    }
}

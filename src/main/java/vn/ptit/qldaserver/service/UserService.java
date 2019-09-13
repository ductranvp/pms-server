package vn.ptit.qldaserver.service;

import vn.ptit.qldaserver.service.dto.AccountDto;
import vn.ptit.qldaserver.service.dto.SignUpDto;
import vn.ptit.qldaserver.domain.User;

import java.util.Optional;

public interface UserService {
    User registerUser(SignUpDto request);

    Optional<User> activateRegistration(String key);

    Optional<User> completePasswordReset(String newPassword, String key);

    Optional<User> requestPasswordReset(String mail);

    User getCurrentUser();

    void updateUser(AccountDto accountDto);

    void initAccount();
}

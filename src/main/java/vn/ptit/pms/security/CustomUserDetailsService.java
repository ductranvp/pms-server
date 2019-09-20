package vn.ptit.pms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Let people login with either username or email
        String lowercaseLogin = usernameOrEmail.toLowerCase(Locale.ENGLISH);
        User user = userRepository.findByUsernameOrEmail(lowercaseLogin, lowercaseLogin)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + lowercaseLogin)
                );
        if (!user.isActivated()){
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }
}

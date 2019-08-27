package vn.ptit.qldaserver.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vn.ptit.qldaserver.entity.JwtBlacklist;
import vn.ptit.qldaserver.entity.User;
import vn.ptit.qldaserver.repository.JwtBlacklistRepository;
import vn.ptit.qldaserver.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vn.ptit.qldaserver.util.Constants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    JwtBlacklistRepository jwtBlacklistRepository;
    @Autowired
    UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtBlacklistRepository jwtBlacklistRepository) {
        super(authManager);
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            JwtBlacklist check = jwtBlacklistRepository.findByToken(token.replace(TOKEN_PREFIX, ""));
            if (check != null) return null;
            String username = JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (username != null) {
                User user = userRepository.findByUsername(username);

                List<GrantedAuthority> authorities = user.getAuthorities().stream().map(authority ->
                        new SimpleGrantedAuthority(authority.getName())
                ).collect(Collectors.toList());

                if (user == null) return null;
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}
package com.checkit.backend.sso.security;

import com.checkit.backend.common.model.persistent.ApplicationUser;
import com.checkit.backend.common.utils.JWTUtils;
import com.checkit.backend.sso.model.JwtAuthenticationToken;
import com.checkit.backend.sso.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Slf4j
@Component
public class SsoAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String jwtSecret;
    private final Long jwtAccessExpirationTimeMillis;
    private final Long jwtRefreshExpirationTimeMillis;

    @Autowired
    public SsoAuthenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     @Value("${jwt.secret}") String jwtSecret,
                                     @Value("${jwt.access-expiration-time-millis}") Long jwtAccessExpirationTimeMillis,
                                     @Value("${jwt.refresh-expiration-time-millis}") Long jwtRefreshExpirationTimeMillis) {

        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtSecret = jwtSecret;
        this.jwtAccessExpirationTimeMillis = jwtAccessExpirationTimeMillis;
        this.jwtRefreshExpirationTimeMillis = jwtRefreshExpirationTimeMillis;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if (token.getPrincipal() == null || token.getCredentials() == null) {
            throw new AuthenticationCredentialsNotFoundException("Empty login or password");
        }

        ApplicationUser applicationUser = userDetailsServiceImpl.loadUserByUsername(token.getName());
        if (applicationUser == null) {
            throw new BadCredentialsException("Wrong username");
        }

        if (!bCryptPasswordEncoder.matches(token.getCredentials().toString(), applicationUser.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        final String username = applicationUser.getUsername();
        final String role = applicationUser.getAuthorities().stream().findFirst().get().toString();

        return new JwtAuthenticationToken(
                applicationUser,
                JWTUtils.generateAccessToken(username,role,jwtSecret,jwtAccessExpirationTimeMillis),
                JWTUtils.generateRefreshToken(username,role,jwtSecret,jwtRefreshExpirationTimeMillis));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

package com.checkit.backend.common.exeption;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public class JwtExpiredException extends AuthenticationException {

    public JwtExpiredException(String message) {
        super(message);
    }
}

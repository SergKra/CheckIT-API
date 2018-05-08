package com.checkit.backend.sso.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public class JwtExpiredException extends AuthenticationException {

    public JwtExpiredException(String message) {
        super(message);
    }
}

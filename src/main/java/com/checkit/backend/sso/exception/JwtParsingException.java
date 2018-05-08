package com.checkit.backend.sso.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Gleb Dovzhenko on 01.05.2018.
 */
public class JwtParsingException extends AuthenticationException {
    public JwtParsingException(String message) {
        super(message);
    }
}

package com.checkit.backend.common.exeption;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Gleb Dovzhenko on 01.05.2018.
 */
public class JwtParsingException extends AuthenticationException {
    public JwtParsingException(String message) {
        super(message);
    }
}

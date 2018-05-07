package com.checkit.backend.common.exeption;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

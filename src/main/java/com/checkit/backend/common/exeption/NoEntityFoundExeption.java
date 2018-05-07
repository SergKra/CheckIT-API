package com.checkit.backend.common.exeption;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public class NoEntityFoundExeption extends RuntimeException {

    public NoEntityFoundExeption(String message) {
        super(message);
    }
}

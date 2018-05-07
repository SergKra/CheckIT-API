package com.checkit.backend.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;

/**
 * Created by Gleb Dovzhenko on 01.05.2018.
 */
@Service
public class CheckValidator {

    private static Validator staticValidator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        staticValidator = factory.getValidator();
    }

    private final Validator validator;

    @Autowired
    public CheckValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.stream().findFirst().get().getMessage());
        }
    }

    public static void staticValidate(Object object) {
        Set<ConstraintViolation<Object>> violations = staticValidator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.stream().findFirst().get().getMessage());
        }
    }
}

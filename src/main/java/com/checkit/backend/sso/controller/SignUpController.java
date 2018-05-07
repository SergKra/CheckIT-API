package com.checkit.backend.sso.controller;

import com.checkit.backend.common.model.persistent.ApplicationUser;
import com.checkit.backend.sso.model.dto.request.SignUpUserRequest;
import com.checkit.backend.sso.model.dto.response.SignUpUserResponse;
import com.checkit.backend.sso.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Gleb Dovzhenko on 22.04.2018.
 */
@RestController
@RequestMapping(value = "/api/signup")
public class SignUpController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public SignUpController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> singUpHandler(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        ApplicationUser registeredUser = userDetailsServiceImpl.registerUser(signUpUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpUserResponse(registeredUser));
    }
}

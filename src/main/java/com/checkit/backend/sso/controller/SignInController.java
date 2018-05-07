package com.checkit.backend.sso.controller;

import com.checkit.backend.common.model.dto.JwtParsed;
import com.checkit.backend.common.model.dto.response.CheckItGenericResponse;
import com.checkit.backend.common.model.dto.response.CheckItResponseType;
import com.checkit.backend.common.utils.JWTUtils;
import com.checkit.backend.sso.model.dto.request.JwtRefreshRequest;
import com.checkit.backend.sso.model.dto.response.JwtResponse;
import com.checkit.backend.sso.service.JwtBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@RestController
@RequestMapping("/api/signin")
public class SignInController {

    private final JwtBlackListService jwtBlackListService;
    private final String jwtSecret;
    private final Long jwtAccessExpirationTimeMillis;
    private final Long jwtRefreshExpirationTimeMillis;

    @Autowired
    public SignInController(JwtBlackListService jwtBlackListService,
                            @Value("${jwt.secret}") String jwtSecret,
                            @Value("${jwt.access-expiration-time-millis}") Long jwtAccessExpirationTimeMillis,
                            @Value("${jwt.refresh-expiration-time-millis}") Long jwtRefreshExpirationTimeMillis) {

        this.jwtBlackListService = jwtBlackListService;
        this.jwtSecret = jwtSecret;
        this.jwtAccessExpirationTimeMillis = jwtAccessExpirationTimeMillis;
        this.jwtRefreshExpirationTimeMillis = jwtRefreshExpirationTimeMillis;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshTokenHandler(@Valid @RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtParsed jwtParsed = JWTUtils.parseToken(jwtRefreshRequest.getRefreshToken(), jwtSecret);

        if (jwtParsed.getId() == null) {
            ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new CheckItGenericResponse(
                                BAD_REQUEST.value(),
                                CheckItResponseType.CLIENT_ERROR,
                                "Wrong token"));
        } else if (jwtBlackListService.isInList(jwtParsed.getId())) {
                    ResponseEntity
                            .status(FORBIDDEN)
                            .body(new CheckItGenericResponse(
                                    FORBIDDEN.value(),
                                    CheckItResponseType.CLIENT_ERROR,
                                    "Token has already been used"));
               }

        jwtBlackListService.addToList(jwtParsed.getId());

        final String username = jwtParsed.getUsername();
        final String role = jwtParsed.getRole().toString();

        return ResponseEntity
                .status(CREATED)
                .body(new JwtResponse(
                        JWTUtils.generateAccessToken(username,role,jwtSecret,jwtAccessExpirationTimeMillis),
                        JWTUtils.generateRefreshToken(username,role,jwtSecret,jwtRefreshExpirationTimeMillis)));
    }
}

package com.checkit.backend.sso.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Getter
@AllArgsConstructor
public class JwtRefreshRequest {

    @NotEmpty(message = "Refresh token must not be empty")
    private String refreshToken;
}

package com.checkit.backend.sso.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Getter
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
}

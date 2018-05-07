package com.checkit.backend.sso.model.dto.response;

import com.checkit.backend.common.model.persistent.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Getter
@AllArgsConstructor
public class OnUserAuthenticatedResponse {

    private Long id;
    private String username;
    private UserRole userRole;
    private String accessToken;
    private String refreshToken;
}

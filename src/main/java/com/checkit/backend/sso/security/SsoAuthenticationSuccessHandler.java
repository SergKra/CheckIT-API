package com.checkit.backend.sso.security;

import com.checkit.backend.common.utils.JsonUtils;
import com.checkit.backend.sso.model.JwtAuthenticationToken;
import com.checkit.backend.sso.model.dto.response.OnUserAuthenticatedResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Component
public class SsoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        JsonUtils.buildJsonResponse(
                httpServletResponse,
                OK.value(),
                new OnUserAuthenticatedResponse(
                        token.getApplicationUser().getId(),
                        token.getApplicationUser().getUsername(),
                        token.getApplicationUser().getRole(),
                        token.getAccessToken(),
                        token.getRefreshToken()));
    }
}

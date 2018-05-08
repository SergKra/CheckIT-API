package com.checkit.backend.sso.security;

import com.checkit.backend.sso.exception.JwtExpiredException;
import com.checkit.backend.common.model.dto.response.CheckItGenericResponse;
import com.checkit.backend.common.model.dto.response.CheckItResponseType;
import com.checkit.backend.common.util.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Component
public class SsoAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
         if (e instanceof JwtExpiredException) {
             JsonUtils.buildJsonResponse(
                     httpServletResponse,
                     HttpStatus.UNAUTHORIZED.value(),
                     new CheckItGenericResponse(
                             HttpStatus.UNAUTHORIZED.value(),
                             CheckItResponseType.TOKEN_EXPIRED,
                             e.getMessage()));
         } else if (e instanceof BadCredentialsException || e instanceof AuthenticationCredentialsNotFoundException) {
                JsonUtils.buildJsonResponse(
                     httpServletResponse,
                     HttpStatus.UNAUTHORIZED.value(),
                     new CheckItGenericResponse(
                             HttpStatus.UNAUTHORIZED.value(),
                             CheckItResponseType.CLIENT_ERROR,
                             e.getMessage()));
                } else {
                    JsonUtils.buildJsonResponse(
                            httpServletResponse,
                            HttpStatus.BAD_REQUEST.value(),
                            new CheckItGenericResponse(
                                    HttpStatus.BAD_REQUEST.value(),
                                    CheckItResponseType.CLIENT_ERROR,
                                    e.getMessage()));
         }
    }
}

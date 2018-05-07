package com.checkit.backend.sso.security;

import com.checkit.backend.common.model.dto.JwtParsed;
import com.checkit.backend.common.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String jwtSecret;
    private final String jwtPrefix;
    private final String jwtHeaderName;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  String jwtSecret,
                                  String jwtPrefix,
                                  String jwtHeaderName) {
        super(authenticationManager);
        this.jwtSecret = jwtSecret;
        this.jwtPrefix = jwtPrefix;
        this.jwtHeaderName = jwtHeaderName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(jwtHeaderName);

        if (header == null || !header.startsWith(jwtPrefix)) {
            chain.doFilter(request,response);
            return;
        }

        final JwtParsed parsed = JWTUtils.parseHeader(header,jwtPrefix,jwtSecret);

        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(parsed.getUsername(),null, Collections.singleton(parsed.getRole())));

        chain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

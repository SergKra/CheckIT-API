package com.checkit.backend.common.utils;

import com.checkit.backend.common.exeption.JwtExpiredException;
import com.checkit.backend.common.exeption.JwtParsingException;
import com.checkit.backend.common.model.dto.JwtParsed;
import com.checkit.backend.common.service.CheckValidator;
import io.jsonwebtoken.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public class JWTUtils {

    // TODO: Store token type in token
    public static String generateAccessToken(String username, String role, String jwtSecret, Long jwtExpirationTimeMillis) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // TODO: Store token type in token
    public static String generateRefreshToken(String username, String role, String jwtSecret, Long jwtExpirationTimeMillis) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setId(UUID.randomUUID().toString())
                .compact();
    }

    public static JwtParsed parseToken(String token, String secret) throws AuthenticationException {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            JwtParsed jwtParsed = new JwtParsed(body.getSubject(), new SimpleGrantedAuthority((String) body.get("role")), body.getId());
            CheckValidator.staticValidate(jwtParsed);
            return jwtParsed;
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("Token is expired");
        } catch (JwtException | ClassCastException e) {
            throw new JwtParsingException("Authentication token cannot be parsed: " + e);
        } catch (ValidationException e) {
            throw new JwtParsingException(e.getMessage());
        }
    }

    public static JwtParsed parseHeader(String header, String headerPrefix, String secret) throws AuthenticationException {
        return parseToken(header.replace(headerPrefix, ""), secret);
    }
}

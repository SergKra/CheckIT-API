package com.checkit.backend.common.utils;

import com.checkit.backend.common.exeption.JwtExpiredException;
import com.checkit.backend.common.exeption.JwtParsingException;
import com.checkit.backend.common.model.dto.JwtParsed;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gleb Dovzhenko on 06.05.2018.
 */
public class JWTUtilsTest {


    private String username = "abc@abc.com";
    private String role = "USER";
    private String secret = "secret";
    private String headerPrefix = "Bearer";
    private Long accessTokenExpirationTimeMills = 2000L;// 2 seconds
    private Long refreshTokenExpirationTimeMills = 8000L;// 8 seconds

    @Test
    public void ifAccessTokenCorrect_thenParseToken() {
        String accessToken = JWTUtils.generateAccessToken(username,role,secret, accessTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(accessToken,secret);
        assertEquals(parsedToken.getUsername(),username);
        assertEquals(parsedToken.getRole().toString(),role);
    }

    @Test(expected = JwtParsingException.class)
    public void ifAccessTokenSecretIncorrect_thenThrowJwtParsingException() {
        String accessToken = JWTUtils.generateAccessToken(username,role,secret, accessTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(accessToken,"not_secret");
    }

    @Test(expected = JwtExpiredException.class)
    public void ifAccessTokenExpired_thenThrowExpiredException() throws InterruptedException {
        String accessToken = JWTUtils.generateAccessToken(username,role,secret, accessTokenExpirationTimeMills);
        Thread.sleep(accessTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(accessToken,secret);
    }

    @Test
    public void ifRefreshTokenCorrect_thenParseToken() {
        String refreshToken = JWTUtils.generateRefreshToken(username,role,secret, refreshTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(refreshToken,secret);
        assertEquals(parsedToken.getUsername(),username);
        assertEquals(parsedToken.getRole().toString(),role);
    }

    @Test
    public void ifRefreshTokenTimeNotFinished_thenParseToken() throws InterruptedException {
        String refreshToken = JWTUtils.generateAccessToken(username,role,secret, refreshTokenExpirationTimeMills);
        Thread.sleep(accessTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(refreshToken,secret);
    }

    @Test(expected = JwtParsingException.class)
    public void ifRefreshTokenSecretIncorrect_thenThrowJwtParsingException() {
        String refreshToken = JWTUtils.generateAccessToken(username,role,secret, refreshTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(refreshToken,"not_secret");
    }

    @Test(expected = JwtExpiredException.class)
    public void ifRefreshTokenExpired_thenThrowExpiredException() throws InterruptedException {
        String refreshToken = JWTUtils.generateAccessToken(username,role,secret, refreshTokenExpirationTimeMills);
        Thread.sleep(refreshTokenExpirationTimeMills);
        JwtParsed parsedToken = JWTUtils.parseToken(refreshToken,secret);
    }

    @Test
    public void ifAccessTokenCorrect_and_HeaderNameCorrect_then_parseHeader() {
        String accessToken = JWTUtils.generateRefreshToken(username,role,secret, refreshTokenExpirationTimeMills);
        String header = headerPrefix + " " + accessToken;
        JwtParsed parsedHeader = JWTUtils.parseHeader(header, headerPrefix, secret);
        assertEquals(parsedHeader.getUsername(),username);
        assertEquals(parsedHeader.getRole().toString(),role);
    }

    @Test(expected = JwtParsingException.class)
    public void ifAccessTokenCorrect_and_HeaderNameIncorrect_then_throwAuthenticationException() {
        String accessToken = JWTUtils.generateRefreshToken(username,role,secret, accessTokenExpirationTimeMills);
        String header = headerPrefix + " " + accessToken;
        JwtParsed parsedHeader = JWTUtils.parseHeader(header, "Unameit", secret);
    }

}
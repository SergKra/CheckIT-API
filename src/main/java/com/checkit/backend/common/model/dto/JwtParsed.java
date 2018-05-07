package com.checkit.backend.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Gleb Dovzhenko on 01.05.2018.
 */
@AllArgsConstructor
@Getter
public class JwtParsed {

    @NotEmpty(message = "Username in JWT must not be empty")
    private String username;
    @NotNull(message = "Role in JWT must not be null")
    private SimpleGrantedAuthority role;
    private String id;
}

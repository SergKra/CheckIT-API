package com.checkit.backend.sso.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Gleb Dovzhenko on 05.05.2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInUserRequest {
    @NotNull(message = "Profile id must not be null")
    private String email;
    @Length(min = 8, max = 32, message = "Wrong password length")
    @NotEmpty(message = "Password must not be empty")
    private String password;
}

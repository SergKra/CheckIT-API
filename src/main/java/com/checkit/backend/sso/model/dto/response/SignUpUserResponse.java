package com.checkit.backend.sso.model.dto.response;

import com.checkit.backend.sso.model.persistent.ApplicationUser;
import lombok.Getter;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
@Getter
public class SignUpUserResponse {

    private Long id;
    //private Long profileId;
    private String email;

    public SignUpUserResponse(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        //this.profileId = applicationUser.getUserData().getId();
        this.email = applicationUser.getEmail();
    }
}

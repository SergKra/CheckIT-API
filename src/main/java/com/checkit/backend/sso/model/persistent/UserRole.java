package com.checkit.backend.sso.model.persistent;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Gleb Dovzhenko on 22.04.2018.
 */
public enum UserRole implements GrantedAuthority {
    USER,MODERATOR,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

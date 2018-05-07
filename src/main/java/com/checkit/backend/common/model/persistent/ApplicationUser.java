package com.checkit.backend.common.model.persistent;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Gleb Dovzhenko on 22.04.2018.
 */

@Entity
@Table(name = "application_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private UserRole role;
    /*@OneToOne
    @JoinColumn(name = "userdata_id")
    private UserData userData;*/

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // Not supported by application
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Not supported by application
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Not supported by application
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Not supported by application
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public ApplicationUser(String password, String email, UserRole role) {
        this.password = password;
        this.email = email;
        //this.userData = new UserData(email);
        this.role = role;
    }

}

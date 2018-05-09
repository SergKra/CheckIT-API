package com.checkit.backend.sso.repository;

import com.checkit.backend.sso.model.persistent.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Gleb Dovzhenko on 30.04.2018.
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);
}

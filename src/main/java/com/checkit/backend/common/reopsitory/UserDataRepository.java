package com.checkit.backend.common.reopsitory;

import com.checkit.backend.common.model.persistent.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Gleb Dovzhenko on 23.04.2018.
 */
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByRegistrationEmail(String username);
}

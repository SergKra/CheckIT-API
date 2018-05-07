package com.checkit.backend.sso.repository;

import com.checkit.backend.sso.model.persistent.JwtBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
public interface JwtBlackListRepository extends JpaRepository<JwtBlackList, Long> {

    Optional<JwtBlackList> findJwtBlackListByUuid(String uuid);
}

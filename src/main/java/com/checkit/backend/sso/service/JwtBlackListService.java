package com.checkit.backend.sso.service;

import com.checkit.backend.sso.model.persistent.JwtBlackList;
import com.checkit.backend.sso.repository.JwtBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Service
public class JwtBlackListService {

    private final JwtBlackListRepository jwtBlackListRepository;

    @Autowired
    public JwtBlackListService(JwtBlackListRepository jwtBlackListRepository) {
        this.jwtBlackListRepository = jwtBlackListRepository;
    }

    public boolean isInList(String uuid) {
        return jwtBlackListRepository.findJwtBlackListByUuid(uuid).isPresent();
    }

    public void addToList(String uuid) {
        jwtBlackListRepository.save(new JwtBlackList(null, uuid, LocalDateTime.now()));
    }
}

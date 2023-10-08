package com.wetalk.repository;

import com.wetalk.domain.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
    Optional<UserToken> findByRefreshToken(String refreshToken);
    Optional<UserToken> deleteByUserId(Long userId);
}

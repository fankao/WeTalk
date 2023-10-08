package com.wetalk.service.registration;

import com.wetalk.domain.User;
import com.wetalk.dto.RefreshToken;
import com.wetalk.dto.SignedInUser;
import com.wetalk.dto.UserReq;

import java.util.Optional;

public interface UserService {
    Optional<SignedInUser> createUser(UserReq user);

    User findUserByUsername(String username);

    SignedInUser getSignedInUser(User userEntity);

    Optional<SignedInUser> getAccessToken(RefreshToken refreshToken);

    void removeRefreshToken(RefreshToken refreshToken);
}

package com.wetalk.service.registration;

import com.wetalk.domain.RoleEnum;
import com.wetalk.domain.User;
import com.wetalk.domain.UserToken;
import com.wetalk.dto.RefreshToken;
import com.wetalk.dto.SignedInUser;
import com.wetalk.dto.UserReq;
import com.wetalk.exception.GenericAlreadyExistsException;
import com.wetalk.exception.InvalidRefreshTokenException;
import com.wetalk.repository.UserRepository;
import com.wetalk.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtManager tokenManager;

    public UserServiceImpl(UserRepository repository, UserTokenRepository userTokenRepository, PasswordEncoder bCryptPasswordEncoder, JwtManager tokenManager) {
        this.repository = repository;
        this.userTokenRepository = userTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenManager = tokenManager;
    }

    @Override
    @Transactional
    public Optional<SignedInUser> createUser(UserReq userReq) {
        Integer count = repository.findByUsernameOrEmail(userReq.username(), userReq.email());
        if(count > 0) {
            log.error("Use different username and email.");
            throw new GenericAlreadyExistsException("Use different username and email.");
        }
        User user = repository.save(toEntity(userReq));
        return Optional.of(createSignedUserWithRefreshToken(user));
    }

    private SignedInUser createSignedUserWithRefreshToken(User user) {
        return createSignedInUser(user)
                .refreshToken(createRefreshToken(user));
    }

    private String createRefreshToken(User user) {
        String token = RandomHolder.randomKey(128);
        userTokenRepository.save(new UserToken()
                .setRefreshToken(token)
                .setUser(user));
        return token;
    }

    private SignedInUser createSignedInUser(User user) {
        String token = tokenManager.create(
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(Objects.nonNull(user.getRole()) ? user.getRole().name() : "")
                        .build());
        return SignedInUser.builder()
                .username(user.getUsername())
                .accessToken(token)
                .userId(user.getId())
                .build();
    }

    private User toEntity(UserReq userReq) {
        return new User(userReq.username(), userReq.email(), bCryptPasswordEncoder.encode(userReq.password()), userReq.profilePicture());
    }

    @Override
    public User findUserByUsername(String username) {
        if(Strings.isBlank(username)) {
            throw new UsernameNotFoundException("Invalid user.");
        }
        final String uname = username.trim();
        return repository.findByUsername(uname)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Given user(%s) not found.", uname)));
    }

    @Override
    @Transactional
    public SignedInUser getSignedInUser(User userEntity) {
        userTokenRepository.deleteByUserId(userEntity.getId());
        return createSignedUserWithRefreshToken(userEntity);
    }

    @Override
    public Optional<SignedInUser> getAccessToken(RefreshToken refreshToken) {
        return userTokenRepository
                .findByRefreshToken(refreshToken.refreshToken())
                .map(ut ->
                        Optional.of(createSignedInUser(ut.getUser()).refreshToken(refreshToken.refreshToken())))
                .orElseThrow(()->new InvalidRefreshTokenException("Invalid token."));
    }

    @Override
    public void removeRefreshToken(RefreshToken refreshToken) {
        userTokenRepository
                .findByRefreshToken(refreshToken.refreshToken())
                .ifPresentOrElse(
                        userTokenRepository::delete,
                        () -> {
                            throw new InvalidRefreshTokenException("Invalid token.");
                        }
                );
    }

    /**
     * This class is responsible for generating random keys.
     */
    // https://stackoverflow.com/a/31214709/109354
    // or can use org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(n)
    private static class RandomHolder {
        static final Random random = new SecureRandom();

        public static String randomKey(int length) {
            return String.format(
                            "%" + length + "s", new BigInteger(length * 5 /*base 32,2^5*/, random).toString(32))
                    .replace('\u0020', '0');
        }
    }
}

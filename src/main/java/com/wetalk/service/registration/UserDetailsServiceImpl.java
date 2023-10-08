package com.wetalk.service.registration;

import com.wetalk.domain.User;
import com.wetalk.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Strings.isBlank(username)) {
            throw new UsernameNotFoundException("Invalid user.");
        }
        final String uname = username.trim();
        Optional<User> oUserEntity = userRepository.findByUsername(uname);
        User userEntity =
                oUserEntity.orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Given user(%s) not found.", uname)));
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole().getAuthority())
                .build();
    }
}

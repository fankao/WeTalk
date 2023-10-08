package com.wetalk.repository;

import com.wetalk.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(
            value =
                    "select count(*) from users u where u.username = :username or u.email = :email",
            nativeQuery = true)
    Integer findByUsernameOrEmail(String username, String email);
}

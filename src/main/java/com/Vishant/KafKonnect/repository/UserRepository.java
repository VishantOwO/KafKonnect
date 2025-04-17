package com.Vishant.KafKonnect.repository;

import com.Vishant.KafKonnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.messages WHERE u.username = :username")
    Optional<User> findByUsernameWithMessages(@Param("username") String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
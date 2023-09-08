package com.woowahanbank.backend.domain.user.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

	Optional<User> findByNickname(String nickname);
}

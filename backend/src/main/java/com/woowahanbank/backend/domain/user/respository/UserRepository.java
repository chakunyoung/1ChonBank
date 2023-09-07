package com.woowahanbank.backend.domain.user.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUserId(String userId);
}

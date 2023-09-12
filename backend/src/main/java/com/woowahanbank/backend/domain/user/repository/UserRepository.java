package com.woowahanbank.backend.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanbank.backend.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserId(String userId);

	Optional<User> findByNickname(String nickname);

	List<User> findByNicknameContaining(String keyword);

}

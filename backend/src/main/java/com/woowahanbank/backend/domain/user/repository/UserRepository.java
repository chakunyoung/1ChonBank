package com.woowahanbank.backend.domain.user.repository;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByNickname(String nickname);

    List<User> findNicknameByNicknameContaining(String keyword);

    List<User> findByFamily(Family family);

}

package com.woowahanbank.backend.domain.family.repository;

import com.woowahanbank.backend.domain.family.domain.Invitation;
import com.woowahanbank.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByToUser_Nickname(String toNickname);

    Optional<Invitation> findByToUser(User toUser);
}

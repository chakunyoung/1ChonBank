package com.woowahanbank.backend.global.notification.repository;

import com.woowahanbank.backend.global.notification.domain.FCMToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FCMTokenRepository extends CrudRepository<FCMToken, String> {
}

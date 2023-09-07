package com.woowahanbank.backend.domain.user.service;

import java.util.Optional;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;

public interface UserService {

	public Optional<User> findByUserId(String userId);

	public void save(JoinDto user);
}

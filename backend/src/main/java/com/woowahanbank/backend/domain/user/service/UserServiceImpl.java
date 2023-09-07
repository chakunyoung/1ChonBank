package com.woowahanbank.backend.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	@Override
	public Optional<User> findByUserId(String userId) {
		return Optional.empty();
	}

	@Override
	public void save(JoinDto user) {

	}
}

package com.woowahanbank.backend.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;
import com.woowahanbank.backend.domain.user.respository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

	private final UserRepository userRepository;

	public Optional<User> findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	public void save(JoinDto joinDto) {
		userRepository.save(joinDto.toEntity(joinDto));
	}
}

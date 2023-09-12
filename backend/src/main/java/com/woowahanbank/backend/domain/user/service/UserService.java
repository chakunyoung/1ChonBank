package com.woowahanbank.backend.domain.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;
import com.woowahanbank.backend.domain.user.dto.SignupDto;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public void signUp() {
		return;
	}

	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}

	public User findByNickname(String nickname) {
		return userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}

	public void save(JoinDto joinDto) {
		userRepository.save(joinDto.toEntity(joinDto));
	}

	public void moneyTransfer(String userId, long money) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		user.moneyTransfer(money);
		userRepository.save(user);
	}

	public SignupDto signup(String userId) {
		User user = userRepository.findByUserId(userId).orElse(null);
		return SignupDto.builder()
			.userId(user.getUserId())
			.nickname(user.getNickname())
			.roles(user.getRoles())
			.build();
	}

	public void saveUser(SignupDto signupDto) {
		User user = userRepository.findByUserId(signupDto.getUserId()).orElse(null);
		user.setUser(signupDto);
		userRepository.save(user);
	}

	public boolean duplicationNickname(String nickname) throws IllegalArgumentException {
		User user = userRepository.findByNickname(nickname).orElse(null);
		if (user == null) {
			return true;
		}
		return false;

	}

	public List<User> findFamily(String keyword){
		List<User> users = userRepository.findByNicknameContaining(keyword);
		return users;
	}

}

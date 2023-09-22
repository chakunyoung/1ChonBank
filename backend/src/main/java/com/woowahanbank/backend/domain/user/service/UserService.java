package com.woowahanbank.backend.domain.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;
import com.woowahanbank.backend.domain.user.dto.SignupDto;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}

	public User findByNickname(String nickname) {
		return userRepository.findByNickname(nickname)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}

	public void userRegister(JoinDto joinDto) {
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
		Family family = user.getFamily();
		String familyName = (family != null) ? family.getFamilyName() : null;
		return SignupDto.builder()
			.userId(user.getUserId())
			.nickname(user.getNickname())
			.roles(user.getRoles())
			.money(user.getMoney())
			.quiz(user.getQuiz())
			.score(user.getScore())
			.familyName(familyName)
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

	@Transactional
	public List<String> findFamily(String keyword) {
		List<User> users = userRepository.findNicknameByNicknameContaining(keyword);
		List<String> userNicknames = new ArrayList<>();
		for (User list : users) {
			userNicknames.add(list.getNickname());
		}
		return userNicknames;
	}

}

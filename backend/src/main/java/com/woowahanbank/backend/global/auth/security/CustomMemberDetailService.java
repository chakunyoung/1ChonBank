package com.woowahanbank.backend.global.auth.security;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {

	private final UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return userService.findByUserId(userId).map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(userId + "는 데이터베이스에 없는 데이터입니다."));
	}

	private UserDetails createUserDetails(User user) {

		String role = user.getRoles().value();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

		return new org.springframework.security.core.userdetails.User(
			String.valueOf(user.getId()),
			null,
			Collections.singleton(grantedAuthority)
		);
	}
}

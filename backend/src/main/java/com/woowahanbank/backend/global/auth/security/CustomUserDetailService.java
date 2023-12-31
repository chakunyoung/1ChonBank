package com.woowahanbank.backend.global.auth.security;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return createUserDetails(userService.findByUserId(userId));
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

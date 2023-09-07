package com.woowahanbank.backend.global.auth.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.woowahanbank.backend.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 부가 상세정보(활성화 여부, 만료, 롤 등) 정의.
 */
@NoArgsConstructor
@AllArgsConstructor
public class CustomMemberDetails implements UserDetails {
	boolean accountNonExpired;
	boolean accountNonLocked;
	boolean credentialNonExpired;
	boolean enabled = false;
	List<GrantedAuthority> roles = new ArrayList<>();
	private User user;

	public CustomMemberDetails(User member) {
		super();
		this.user = user;
		this.roles = new ArrayList<>(); //
	}

	public User getUser() {
		return this.user;
	}

	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	// @Override
	// public Collection<? extends GrantedAuthority> getAuthorities() {
	// 	return this.roles;
	// }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoleList().forEach(r -> {
			authorities.add(() -> {
				return r;
			});
		});
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> roles) {
		this.roles = roles;
	}

	// public String getNickname() {
	// 	return this.user.getNickname();
	// }
}

package com.woowahanbank.backend.domain.user.dto;

import com.woowahanbank.backend.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {

	private String userId;
	private String email;
	private String nickname;
	private long money;

	public User toEntity(JoinDto joinDto) {
		return User.builder()
			.userId(userId)
			.nickname(nickname)
			.money(money)
			.build();
	}
}

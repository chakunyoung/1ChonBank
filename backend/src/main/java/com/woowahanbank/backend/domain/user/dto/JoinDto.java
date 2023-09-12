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
	private long money;
	private int score;

	public User toEntity(JoinDto joinDto) {
		return User.builder()
			.userId(userId)
			.score(score)
			.money(money)
			.build();
	}
}

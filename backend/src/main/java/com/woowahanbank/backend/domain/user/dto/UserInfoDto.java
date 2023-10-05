package com.woowahanbank.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
	private String userId;
	private String nickname;
	private String roles;
	private Long money;
	private Long quiz;
	private int score;
	private String familyName;
	private Long characterNum;
}

package com.woowahanbank.backend.domain.user.dto;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.user.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class SignupDto {

	private String userId;

	private String nickname;

	private Role roles;

	private Long money;

	private Long quiz;

	private Integer score;

	private String familyName;

	private Long characterNum;
}

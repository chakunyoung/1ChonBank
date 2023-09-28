package com.woowahanbank.backend.domain.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.dto.FamilyUserDto;
import com.woowahanbank.backend.domain.user.dto.SignupDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "`user`")
@Entity(name = "user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 우리 서비스 닉네임
	private String nickname;

	// OAuth sub identity value
	private String userId;

	@ManyToOne
	@JoinColumn(name = "family_id")
	private Family family;

	@Column(name = "money", nullable = false, columnDefinition = "bigint default 0")
	private Long money;

	private Long quiz;

	private Integer score;

	@Enumerated(EnumType.STRING)
	private Role roles;

	private Long characterNum;

	public void authorizeRole(Role role) {
		this.roles = role;
	}

	public List<String> getRoleList() {
		return new ArrayList<>();
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public void moneyTransfer(long money) {
		this.money += money;
	}

	public void setUser(SignupDto signupDto) {
		this.nickname = signupDto.getNickname();
		this.roles = signupDto.getRoles();
		this.characterNum=signupDto.getCharacterNum();
	}

	public FamilyUserDto toFamilyUserDto() {
		return FamilyUserDto.builder()
			.nickname(this.nickname)
			.role(this.roles)
			.money(this.money)
			.build();
	}

	public void solvedQuiz(Long quiz) {
		this.quiz = quiz;
	}

	public void updatePoint(Long plusMoney) {
		this.money +=plusMoney;
	}
}

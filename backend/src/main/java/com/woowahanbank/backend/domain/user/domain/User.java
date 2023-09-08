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

import com.sun.istack.NotNull;
import com.woowahanbank.backend.domain.family.domain.Family;

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
	private Long id;

	private String userId;

	@ManyToOne
	@JoinColumn(name = "family_id")
	private Family family;

	@NotNull
	private String nickname;

	@Column(name = "money", nullable = false, columnDefinition = "bigint default 0")
	private Long money;

	@Enumerated(EnumType.STRING)
	private Role type;

	private Long quiz;

	private Long score;

	@Enumerated(EnumType.STRING)
	private Role roles;

	public List<String> getRoleList() {
		return new ArrayList<>();
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public void moneyTransfer(long money) {
		this.money += money;
	}

}

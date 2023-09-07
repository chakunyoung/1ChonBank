package com.woowahanbank.backend.domain.user.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

	@ManyToOne
	@JoinColumn(name = "family_id")
	private Family family;

	private String email;

	@NotNull
	private String nickname;

	private String password;

	@Column(name = "money", nullable = false, columnDefinition = "bigint default 0")
	private Long money;

	private Long age;

	@Enumerated(EnumType.STRING)
	private UserType type;

	private Long quiz;

	private Long score;

	private String userId;

	private String roles;

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}

	public void setFamily(Family family) {
		this.family = family;
	}

}

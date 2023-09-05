package com.woowahanbank.backend.domain.user.domain;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.*;

@Entity(name = "user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @ManyToOne
	// @JoinColumn(name = "family_id")
	// private Family family;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@Column(name = "money", nullable = false, columnDefinition = "bigint default 0")
	private Long money;

	@NotNull
	private String name;

	private Long age;

	@NotNull
	private String type;

	private Long quiz;

	private Long score;

}

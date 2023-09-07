package com.woowahanbank.backend.domain.user.domain;

public enum Role {
	ROLE_PARENT("ROLE_PARENT"),
	ROLE_CHILD("ROLE_CHILD"),
	ROLE_ADMIN("ROLE_ADMIN");

	String role;

	Role(String role) {
		this.role = role;
	}

	public String value() {
		return role;
	}

}


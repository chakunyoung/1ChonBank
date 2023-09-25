package com.woowahanbank.backend.domain.customer.service;

import java.util.List;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

public interface CustomerService<T> {
	void apply(T tDto);

	List<T> getDisallow(CustomUserDetails customUser);

	void allow(Long id, User parent);

	void refuse(Long id, User parent);

	List<T> getDisallowProducts(Long productId);
}

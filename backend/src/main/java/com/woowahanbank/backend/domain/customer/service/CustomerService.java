package com.woowahanbank.backend.domain.customer.service;

import java.util.List;

import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

public interface CustomerService<T> {
	void apply(T tDto);

	List<T> getDisallow(CustomUserDetails customUser);

	void allow(Long id);

	void refuse(Long id);

	List<T> getDisallowProducts(Long productId);
}

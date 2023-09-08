package com.woowahanbank.backend.domain.customer.service;

import java.util.List;

import com.woowahanbank.backend.domain.user.dto.UserDto;

public interface CustomerService<T> {
	void apply(T tDto, UserDto userDto);

	List<T> getDisallow(UserDto userDto);

	void allow(Long id);

	void refuse(Long id);
}

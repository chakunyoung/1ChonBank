package com.woowahanbank.backend.domain.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.service.CustomerService;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savingser")
public class SavingserController {
	@Qualifier("savingserServiceImpl")
	private final CustomerService<SavingserDto> customerService;

	@PostMapping
	public ResponseEntity<?> applySavingser(@RequestBody SavingserDto savingserDto,
		@RequestBody UserDto userDto) {
		try {
			customerService.apply(savingserDto, userDto);
			return BaseResponse.ok(HttpStatus.OK, "적금 상품 등록 성공");
		} catch (Exception e) {
			return BaseResponse.fail("적금 상품 등록 실패", 400);
		}
	}

	@GetMapping("/disallowList")
	public ResponseEntity<?> getDisallow(@RequestBody UserDto userDto) {
		List<SavingserDto> disallowList = customerService.getDisallow(userDto);
		return BaseResponse.okWithData(HttpStatus.OK, "우리 가족 불허 적금 상품", disallowList);
	}

	@PutMapping("/allow/{savingsId}")
	public ResponseEntity<?> allowProduct(@PathVariable Long savingsId) {
		try {
			customerService.allow(savingsId);
			return BaseResponse.ok(HttpStatus.OK, "적금 수락 성공");
		} catch (Exception e) {
			return BaseResponse.fail("적금 수락 실패", 400);
		}
	}

	@PutMapping("/refuse/{savingsId}")
	public ResponseEntity<?> refuseProduct(@PathVariable Long savingsId) {
		try {
			customerService.refuse(savingsId);
			return BaseResponse.ok(HttpStatus.OK, "적금 거절 성공");
		} catch (Exception e) {
			return BaseResponse.fail("적금 거절 실패", 400);
		}
	}
}

package com.woowahanbank.backend.domain.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.service.CustomerService;
import com.woowahanbank.backend.domain.customer.service.DepositMoneyService;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/depositor")
public class DepositorController {
	private final CustomerService<DepositorDto> customerService;
	private final DepositMoneyService depositMoneyService;

	@PostMapping
	public ResponseEntity<?> apply(@RequestBody DepositorDto depositorDto) {
		System.out.println(depositorDto);
		try {
			customerService.apply(depositorDto);
			return BaseResponse.ok(HttpStatus.OK, "예금 상품 등록 성공");
		} catch (Exception e) {
			return BaseResponse.fail("예금 상품 등록 실패", 400);
		}
	}

	@GetMapping("/disallowList")
	public ResponseEntity<?> getDisallow(@RequestBody UserDto userDto) {
		List<DepositorDto> disallowList = customerService.getDisallow(userDto);
		return BaseResponse.okWithData(HttpStatus.OK, "우리 가족 불허 예금 상품", disallowList);
	}

	@PutMapping("/allow/{depositorId}")
	public ResponseEntity<?> allowProduct(@PathVariable Long depositorId) {
		try {
			customerService.allow(depositorId);
			return BaseResponse.ok(HttpStatus.OK, "예금 수락 성공");
		} catch (Exception e) {
			return BaseResponse.fail("예금 수락 실패", 400);
		}
	}

	@PutMapping("/refuse/{depositorId}")
	public ResponseEntity<?> refuseProduct(@PathVariable Long depositorId) {
		try {
			customerService.refuse(depositorId);
			return BaseResponse.ok(HttpStatus.OK, "예금 거절 성공");
		} catch (Exception e) {
			return BaseResponse.fail("예금 거절 실패", 400);
		}
	}

	@PutMapping("/deposit/{depositorId}/{money}")
	public ResponseEntity<?> depositMoney(@RequestBody UserDto userDto, @PathVariable Long depositorId,
		@PathVariable int money) {
		try {
			depositMoneyService.depositMoney(money, depositorId, userDto.getId());
			return BaseResponse.ok(HttpStatus.OK, "입/출금 성공");
		} catch (Exception e) {
			return BaseResponse.fail("입/출금 실패", 400);
		}
	}
}

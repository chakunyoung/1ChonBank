package com.woowahanbank.backend.domain.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.service.CustomerService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/depositor")
public class DepositorController {
	private final CustomerService<DepositorDto> customerService;

	@PostMapping
	public ResponseEntity<?> apply(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody DepositorDto depositorDto) {
		depositorDto.setUserId(customUser.getUser().getId());
		try {
			customerService.apply(depositorDto);
			return BaseResponse.ok(HttpStatus.OK, "예금 상품 등록 성공");
		} catch (Exception e) {
			return BaseResponse.fail("예금 상품 등록 실패", 400);
		}
	}

	@GetMapping("/disallowList")
	public ResponseEntity<?> getDisallow(@AuthenticationPrincipal CustomUserDetails customUser) {
		List<DepositorDto> disallowList = customerService.getDisallow(customUser);
		return BaseResponse.okWithData(HttpStatus.OK, "우리 가족 불허 예금 상품", disallowList);
	}

	@GetMapping("/disallowCustommer/{productId}")
	public ResponseEntity<?> getDisallow(@PathVariable Long productId) {
		List<DepositorDto> disallowList = customerService.getDisallowProducts(productId);
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
}

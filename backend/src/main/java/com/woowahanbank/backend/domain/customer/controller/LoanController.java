package com.woowahanbank.backend.domain.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
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

import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.service.CustomerService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loaner")
public class LoanController {
	@Qualifier("loanerServiceImpl")
	private final CustomerService<LoanerDto> customerService;

	@PostMapping
	public ResponseEntity<?> apply(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody LoanerDto loanerDto) {
		loanerDto.setUserId(customUser.getUser().getId());
		System.out.println("=============================================");
		System.out.println(loanerDto);
		try {
			customerService.apply(loanerDto);
			return BaseResponse.ok(HttpStatus.OK, "대출 상품 등록 성공");
		} catch (Exception e) {
			return BaseResponse.fail("대출 상품 등록 실패", 400);
		}
	}

	@GetMapping("/disallowList")
	public ResponseEntity<?> getDisallow(@AuthenticationPrincipal CustomUserDetails customUser) {
		List<LoanerDto> disallowList = customerService.getDisallow(customUser);
		return BaseResponse.okWithData(HttpStatus.OK, "우리 가족 불허 대출 상품", disallowList);
	}

	@PutMapping("/allow/{loanerId}")
	public ResponseEntity<?> allowProduct(@PathVariable Long loanerId) {
		try {
			customerService.allow(loanerId);
			return BaseResponse.ok(HttpStatus.OK, "대출 수락 성공");
		} catch (Exception e) {
			return BaseResponse.fail("대출 수락 실패", 400);
		}
	}

	@PutMapping("/refuse/{loanerId}")
	public ResponseEntity<?> refuseProduct(@PathVariable Long loanerId) {
		try {
			customerService.refuse(loanerId);
			return BaseResponse.ok(HttpStatus.OK, "대출 거절 성공");
		} catch (Exception e) {
			return BaseResponse.fail("대출 거절 실패", 400);
		}
	}

}

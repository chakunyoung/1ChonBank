package com.woowahanbank.backend.domain.banking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.banking.dto.Amount;
import com.woowahanbank.backend.domain.banking.dto.ChildPinMoney;
import com.woowahanbank.backend.domain.banking.service.BankingService;
import com.woowahanbank.backend.domain.user.domain.Role;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
public class BankingController {

	private final BankingService bankingService;

	@GetMapping("/depositor")
	public ResponseEntity<?> getDepositors(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		return BaseResponse.okWithData(HttpStatus.OK, "예금 목록을 조회했습니다.",
			bankingService.getDepositorList(customUserDetails.getUser()));
	}

	@GetMapping("/loaner")
	public ResponseEntity<?> getLoaners(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		return BaseResponse.okWithData(HttpStatus.OK, "대출 목록을 조회했습니다.",
			bankingService.getLoanerList(customUserDetails.getUser()));
	}

	@GetMapping("/saving")
	public ResponseEntity<?> getSavings(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		return BaseResponse.okWithData(HttpStatus.OK, "적금 목록을 조회했습니다.",
			bankingService.getSavingList(customUserDetails.getUser()));
	}

	@PostMapping("/charge/money")
	public ResponseEntity<?> chargePoint(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		Amount amount) {
		bankingService.pointTransfer(customUserDetails.getUser(), amount.getAmount(), Role.ROLE_PARENT);
		return BaseResponse.ok(HttpStatus.OK, "포인트가 충전되었습니다.");
	}

	// todo: 확인필요 -> 포인트 출금을 부모가 허락해줘야함
	@PostMapping("/withdraw/money")
	public ResponseEntity<?> withdrawPoint(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		Amount amount) {
		bankingService.pointTransfer(customUserDetails.getUser(), -amount.getAmount(), Role.ROLE_CHILD);
		return BaseResponse.ok(HttpStatus.OK, "포인트 출금을 신청했습니다.");
	}

	@PostMapping("/pinmoney")
	public ResponseEntity<?> pinMoney(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		ChildPinMoney childPinMoney) {
		bankingService.assignNewPinMoney(childPinMoney);
		return BaseResponse.ok(HttpStatus.OK, "용돈을 받았습니다.");
	}

}

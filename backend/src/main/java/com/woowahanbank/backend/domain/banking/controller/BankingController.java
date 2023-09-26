package com.woowahanbank.backend.domain.banking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.banking.dto.ChildPinMoney;
import com.woowahanbank.backend.domain.banking.dto.PaymentRequestDto;
import com.woowahanbank.backend.domain.banking.dto.PaymentResponseDto;
import com.woowahanbank.backend.domain.banking.service.BankingService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
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

	//	@PostMapping("/charge/money")
	//	public ResponseEntity<?> chargePoint(@AuthenticationPrincipal CustomUserDetails customUserDetails,
	//		@RequestBody Amount amount) {
	//		bankingService.pointTransfer(customUserDetails.getUser(), amount.getMoneyAmount());
	//		return BaseResponse.ok(HttpStatus.OK, "포인트가 충전되었습니다.");
	//	}

	// todo: 알람 기능 완성 시 추가 작업 필요함
	@PostMapping("/withdraw/money")
	public ResponseEntity<?> withdrawPoint(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody PaymentRequestDto paymentRequestDto) {
		/**
		 * 결제 url 응답이 오는데 이걸 부모한테 쏴야함
		 */
		PaymentResponseDto paymentResponseDto = bankingService.makePayment(paymentRequestDto.getMoneyAmount());

		bankingService.pointTransfer(customUserDetails.getUser(), paymentRequestDto.getMoneyAmount());
		return BaseResponse.ok(HttpStatus.OK, "포인트 출금을 신청했습니다.");
	}

	@PostMapping("/pinmoney")
	public ResponseEntity<?> pinMoney(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody ChildPinMoney childPinMoney) {
		log.info("{}", childPinMoney);
		bankingService.assignNewPinMoney(childPinMoney);
		return BaseResponse.ok(HttpStatus.OK, "어린이에게 새로운 용돈 지급날을 결정했습니다.");
	}
}

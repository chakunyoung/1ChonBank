package com.woowahanbank.backend.domain.banking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanbank.backend.domain.banking.domain.PinMoney;
import com.woowahanbank.backend.domain.banking.dto.ChildPinMoney;
import com.woowahanbank.backend.domain.banking.dto.PaymentResponseDto;
import com.woowahanbank.backend.domain.banking.repository.PinMoneyRepository;
import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.service.DepositorServiceImpl;
import com.woowahanbank.backend.domain.customer.service.LoanerServiceImpl;
import com.woowahanbank.backend.domain.customer.service.SavingserServiceImpl;
import com.woowahanbank.backend.domain.user.domain.Role;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankingService {

	@Value("${payment.key}")
	private String appKey;
	private final UserRepository userRepository;
	private final PinMoneyRepository pinMoneyRepository;
	private final DepositorServiceImpl depositorService;
	private final LoanerServiceImpl loanerService;
	private final SavingserServiceImpl savingserService;

	public PaymentResponseDto makePayment(Long amount) {
		RestTemplate rt = new RestTemplate();

		// Set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", appKey);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Set body
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", "TC0ONETIME");
		body.add("partner_order_id", "19293819293928");
		body.add("partner_user_id", "1234");
		body.add("item_name", "우아한 뱅크");
		body.add("quantity", "0");
		body.add("total_amount", String.valueOf(amount));
		body.add("tax_free_amount", String.valueOf(amount));
		body.add("approval_url", "http://localhost:8080/ok");
		body.add("cancel_url", "http://localhost:8080/cancel");
		body.add("fail_url", "http://localhost:8080/fail");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		// Send POST request
		ResponseEntity<String> response = rt.exchange(
			"https://kapi.kakao.com/v1/payment/ready",
			HttpMethod.POST,
			request,
			String.class
		);

		// Handle response
		if (response.getStatusCodeValue() == 200) {
			// Successfully received response
			String responseBody = response.getBody();

			ObjectMapper objectMapper = new ObjectMapper();
			PaymentResponseDto paymentResponseDto = null;
			try {
				paymentResponseDto = objectMapper.readValue(responseBody, PaymentResponseDto.class);
			} catch (JsonProcessingException e) {
				throw new IllegalArgumentException(e);
			}
			return paymentResponseDto;
		} else {
			throw new IllegalArgumentException("API 요청이 실패했습니다");
		}
	}

	@Transactional
	public void pointTransfer(User user, long amount) {
		User userdb = userRepository.findByNickname(user.getNickname()).orElseThrow(IllegalArgumentException::new);

		if (amount > userdb.getMoney() && user.getRoles() == Role.ROLE_CHILD) {
			throw new IllegalArgumentException("포인트가 부족합니다.");
		}

		user.moneyTransfer(-amount);
		userRepository.save(user);
	}

	@Transactional
	public void assignNewPinMoney(ChildPinMoney childPinMoneyDto) {
		log.info("{}", childPinMoneyDto.getChildNickname());
		User childUser = userRepository.findByNickname(childPinMoneyDto.getChildNickname())
			.orElseThrow(() -> new IllegalArgumentException("어린이에 해당되는 유저가 없습니다."));

		pinMoneyRepository.findByUser(childUser)
			.ifPresent(pinMoney -> pinMoneyRepository.deleteById(pinMoney.getId()));

		// 당일 용돈 주기
		childUser.moneyTransfer(childPinMoneyDto.getPinMoney());

		// 다음 용돈일 지정
		PinMoney newPinMoney = PinMoney.builder()
			.user(childUser)
			.pinMoney(childPinMoneyDto.getPinMoney())
			.receiveTime(childPinMoneyDto.getReceiveTime().plusDays(7))
			.build();

		log.info("{}", newPinMoney);

		pinMoneyRepository.save(newPinMoney);
	}

	public List<DepositorDto> getDepositorList(User user) {
		return depositorService.getDepositorList(user);
	}

	public List<LoanerDto> getLoanerList(User user) {
		return loanerService.getLoanerList(user);
	}

	public List<SavingserDto> getSavingList(User user) {
		return savingserService.getSavingList(user);
	}

	public void updatePinMoneyAndTransfer() {
		List<PinMoney> pinMoneyList = pinMoneyRepository.findAllByReceiveTime(LocalDate.now());
		pinMoneyList.forEach(pinMoney -> {
			pinMoney.nextPinMoneyDay();
			pinMoney.getUser().moneyTransfer(pinMoney.getPinMoney());
			pinMoneyRepository.save(pinMoney);
		});
	}

}

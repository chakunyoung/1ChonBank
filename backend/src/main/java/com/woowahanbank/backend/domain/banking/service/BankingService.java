package com.woowahanbank.backend.domain.banking.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.banking.domain.PinMoney;
import com.woowahanbank.backend.domain.banking.dto.ChildPinMoney;
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
import com.woowahanbank.backend.global.exception.custom.ForbiddenException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankingService {

	private final UserRepository userRepository;
	private final PinMoneyRepository pinMoneyRepository;
	private final DepositorServiceImpl depositorService;
	private final LoanerServiceImpl loanerService;
	private final SavingserServiceImpl savingserService;

	@Transactional
	public void pointTransfer(User user, long amount, Role requiredRole) {
		if (user.getRoles() != requiredRole)
			throw new ForbiddenException("접근이 거부되었습니다: 권한이 없습니다.");

		User userdb = userRepository.findByNickname(user.getNickname()).orElseThrow(IllegalArgumentException::new);

		if (amount > userdb.getMoney() && user.getRoles() == Role.ROLE_CHILD) {
			throw new IllegalArgumentException("포인트가 부족합니다.");
		}

		user.moneyTransfer(amount);
		userRepository.save(user);
	}

	@Transactional
	public void assignNewPinMoney(ChildPinMoney childPinMoneyDto) {
		User childUser = userRepository.findByNickname(childPinMoneyDto.getChildNickname())
			.orElseThrow(() -> new IllegalArgumentException("어린이에 해당되는 유저가 없습니다."));

		pinMoneyRepository.findByUser(childUser)
			.ifPresent(pinMoney -> pinMoneyRepository.deleteById(pinMoney.getId()));

		PinMoney newPinMoney = PinMoney.builder()
			.user(childUser)
			.pinMoney(childPinMoneyDto.getPinMoney())
			.receiveTime(childPinMoneyDto.getReceiveTime())
			.build();

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

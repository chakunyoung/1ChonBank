package com.woowahanbank.backend.domain.customer.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Depositor;
import com.woowahanbank.backend.domain.customer.repository.DepositorRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositMoneyServiceImpl implements DepositMoneyService {
	private final DepositorRepository depositorRepository;
	private final UserRepository userRepository;

	@Override
	public void depositMoney(int money, Long depositorId, Long UserId) {
		User depositUser = userRepository.findById(UserId).get();
		Depositor depositor = depositorRepository.findById(depositorId).get();
		if (depositor.getMoney() + money < 0)
			throw new ArithmeticException("입금 금액 보다 많은 금액을 뽑으려고 합니다.");
		depositor.depositMoney(money);
		//유저가 가진 금액을 변경하는 함수 (들어가야됨!!!!)
		userRepository.save(depositUser);
		depositorRepository.save(depositor);
	}
}

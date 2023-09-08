package com.woowahanbank.backend.domain.customer.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Depositor;
import com.woowahanbank.backend.domain.customer.repository.DepositorRepository;
import com.woowahanbank.backend.domain.point.service.PointServiceImpl;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositMoneyServiceImpl implements DepositMoneyService {
	private final DepositorRepository depositorRepository;
	private final UserRepository userRepository;
	private final PointServiceImpl pointService;

	@Override
	public void depositMoney(int money, Long depositorId, Long userId) {
		User depositUser = userRepository.findById(userId).get();
		Depositor depositor = depositorRepository.findById(depositorId).get();
		if (depositor.getMoney() + money < 0)
			throw new ArithmeticException("입금 금액 보다 많은 금액을 뽑으려고 합니다.");
		depositor.depositMoney(money);
		User child = userRepository.findById(userId).get();
		child.moneyTransfer(money);
		userRepository.save(child);
		User admin = userRepository.findById(1L).get(); // 가상의 admin 유저
		if (money > 0)
			pointService.makePoint(child, admin, "예금", money);
		if (money < 0)
			pointService.makePoint(admin, child, "출금", -money);
		userRepository.save(depositUser);
		depositorRepository.save(depositor);
	}
}

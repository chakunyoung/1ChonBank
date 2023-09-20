package com.woowahanbank.backend.domain.customer.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Loaner;
import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.repository.LoanerRepository;
import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.financialproducts.repository.FinancialProductRepository;
import com.woowahanbank.backend.domain.point.service.PointServiceImpl;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanerServiceImpl implements CustomerService<LoanerDto> {
	private final LoanerRepository loanerRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;
	private final PointServiceImpl pointService;

	@Override
	public void apply(LoanerDto loanerDto) {
		User user = userRepository.findById(loanerDto.getUserId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(loanerDto.getFinancialProductId())
			.get();
		Loaner loaner = Loaner.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(loanerDto.getMoney())
			.build();
		loanerRepository.save(loaner);
	}

	@Override
	public List<LoanerDto> getDisallow(CustomUserDetails customUser) {
		List<Loaner> list = loanerRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			customUser.getUser().getFamily().getId());
		List<LoanerDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	@Override
	public void allow(Long id) {
		Loaner loaner = loanerRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(loaner.getFinancialProduct().getId())
			.get();
		loaner.allow();
		loaner.changeDate();
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		ThreadPoolTaskScheduler endS = new ThreadPoolTaskScheduler();
		tpts.initialize();
		endS.initialize();
		String dayDate = loaner.getDate().format(DateTimeFormatter.ofPattern("d")).toString();
		User admin = userRepository.findById(1).get(); // 가상의 admin 유저
		User parent = userRepository.findById(financialProduct.getParent().getId()).get();
		User child = userRepository.findById(loaner.getUser().getId()).get();
		tpts.schedule(() -> {
			int money = loaner.getMoney() * financialProduct.getRate() / 100; // 대출이자
			child.moneyTransfer(-money);
			userRepository.save(child);
			pointService.makePoint(child, admin, "대출 이자 납부", money);
			parent.moneyTransfer(money);
			userRepository.save(parent);
			pointService.makePoint(admin, parent, "대출 이자", money);
		}, new CronTrigger("0 0 0 " + dayDate + " * ?"));
		String endDate = loaner.getDate()
			.plus(financialProduct.getPeriod() + 1, ChronoUnit.MONTHS)
			.format(DateTimeFormatter.ofPattern("d M e"))
			.toString();
		endS.schedule(() -> {
			int money = loaner.getMoney();
			child.moneyTransfer(-money);
			userRepository.save(child);
			pointService.makePoint(child, admin, "대출금 납부", money);
			parent.moneyTransfer(money);
			userRepository.save(parent);
			pointService.makePoint(admin, parent, "대출금 상환", money);
			loanerRepository.delete(loaner);
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		loanerRepository.save(loaner);
	}

	@Override
	public void refuse(Long id) {
		loanerRepository.deleteById(id);
	}

	public List<LoanerDto> getLoanerList(User user) {
		return loanerRepository.findByUser(user).stream().map(this::changeToDto)
			.collect(Collectors.toList());
	}

	private LoanerDto changeToDto(Loaner loaner) {
		FinancialProduct financialProduct = financialProductRepository.findById(
			loaner.getFinancialProduct().getId()).get();
		return LoanerDto.builder()
			.id(loaner.getId())
			.userId(loaner.getUser().getId())
			.money(loaner.getMoney())
			.date(loaner.getDate())
			.financialProductId(financialProduct.getId())
			.productName(financialProduct.getName())
			.build();
	}
}

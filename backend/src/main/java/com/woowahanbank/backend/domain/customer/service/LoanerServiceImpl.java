package com.woowahanbank.backend.domain.customer.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Loaner;
import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.repository.LoanerRepository;
import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.financialproducts.repository.FinancialProductRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.domain.user.respository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanerServiceImpl implements CustomerService<LoanerDto> {
	private final LoanerRepository loanerRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;

	@Override
	public void apply(LoanerDto loanerDto, UserDto userDto) {
		User user = userRepository.findById(userDto.getId()).get();
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
	public List<LoanerDto> getDisallow(UserDto userDto) {
		List<Loaner> list = loanerRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			userDto.getFamilyId());
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
		tpts.schedule(() -> {
			int money = loaner.getMoney() * financialProduct.getRate() / 100; // 대출이자
			// 아이 돈 변경() - 대출이자 빼기
			// 부모 돈 변경() - 대출이자 넣기
			// 거래내역()
		}, new CronTrigger("0 0 0 " + dayDate + " ?"));
		String endDate = loaner.getDate()
			.plus(financialProduct.getPeriod() + 1, ChronoUnit.DAYS)
			.format(DateTimeFormatter.ofPattern("d M e yyyy"))
			.toString();
		endS.schedule(() -> {
			// 아이 돈 변경() - 대출금액 전부 빼기
			// 부모 돈 변경() - 대출금액 전부 넣기
			// 해당 loaner 삭제
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		loanerRepository.save(loaner);
	}

	@Override
	public void refuse(Long id) {
		loanerRepository.deleteById(id);
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
			.ProductName(financialProduct.getName())
			.build();
	}
}

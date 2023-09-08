package com.woowahanbank.backend.domain.customer.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Depositor;
import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.repository.DepositorRepository;
import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.financialproducts.repository.FinancialProductRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.domain.user.respository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositorServiceImpl implements CustomerService<DepositorDto> {
	private final DepositorRepository depositorRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;

	@Override
	public void apply(DepositorDto depositorDto, UserDto userDto) {
		User user = userRepository.findById(userDto.getId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(depositorDto.getFinancialProductId())
			.get();
		Depositor depositor = Depositor.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(depositorDto.getMoney())
			.build();
		depositorRepository.save(depositor);
	}

	@Override
	public List<DepositorDto> getDisallow(UserDto userDto) {
		List<Depositor> list = depositorRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			userDto.getFamilyId());
		List<DepositorDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	@Override
	public void allow(Long id) {
		Depositor depositor = depositorRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(depositor.getFinancialProduct().getId())
			.get();
		depositor.allow();
		depositor.changeDate();
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		ThreadPoolTaskScheduler endS = new ThreadPoolTaskScheduler();
		tpts.initialize();
		endS.initialize();
		String dayDate = depositor.getDate().format(DateTimeFormatter.ofPattern("d")).toString();
		tpts.schedule(() -> {
			int money = depositor.getMoney() * financialProduct.getRate() / 100; // 이자
			depositor.depositMoney(money);
			//부모 돈 변경() - 이자 빼기
			//거래내역
		}, new CronTrigger("0 0 0 " + dayDate + " ?"));
		String endDate = depositor.getDate()
			.plus(financialProduct.getPeriod() + 1, ChronoUnit.DAYS)
			.format(DateTimeFormatter.ofPattern("d M e yyyy"))
			.toString();
		endS.schedule(() -> {
			// 자식 금액 변경() - 모든 금액 반환
			// depositor 객체 삭제
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		depositorRepository.save(depositor);
	}

	@Override
	public void refuse(Long id) {
		depositorRepository.deleteById(id);
	}

	private DepositorDto changeToDto(Depositor depositor) {
		FinancialProduct financialProduct = financialProductRepository.findById(
			depositor.getFinancialProduct().getId()).get();
		return DepositorDto.builder()
			.id(depositor.getId())
			.userId(depositor.getUser().getId())
			.money(depositor.getMoney())
			.date(depositor.getDate())
			.financialProductId(financialProduct.getId())
			.ProductName(financialProduct.getName())
			.build();
	}

}

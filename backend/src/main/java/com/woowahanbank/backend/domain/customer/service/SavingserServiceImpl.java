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

import com.woowahanbank.backend.domain.customer.domain.Savingser;
import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.repository.SavingserRepository;
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
public class SavingserServiceImpl implements CustomerService<SavingserDto> {
	private final SavingserRepository savingserRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;
	private final PointServiceImpl pointService;

	@Override
	public void apply(SavingserDto savingserDto) {
		User user = userRepository.findById(savingserDto.getUserId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingserDto.getFinancialProductId())
			.get();
		Savingser savingser = Savingser.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(0)
			.regularMoney(savingserDto.getRegularMoney())
			.build();
		savingserRepository.save(savingser);
	}

	@Override
	public List<SavingserDto> getDisallow(CustomUserDetails customUser) {
		List<Savingser> list = savingserRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			customUser.getUser().getFamily().getId());
		List<SavingserDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	@Override
	public void allow(Long id) {
		Savingser savingser = savingserRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingser.getFinancialProduct().getId())
			.get();
		savingser.allow();
		savingser.changeDate();
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		ThreadPoolTaskScheduler endS = new ThreadPoolTaskScheduler();
		tpts.initialize();
		endS.initialize();
		String dayDate = savingser.getDate().format(DateTimeFormatter.ofPattern("d")).toString();
		User admin = userRepository.findById(1).get(); // 가상의 admin 유저
		User parent = userRepository.findById(financialProduct.getParent().getId()).get();
		User child = userRepository.findById(savingser.getUser().getId()).get();
		tpts.schedule(() -> {
			int regMoney = savingser.getRegularMoney();
			child.moneyTransfer(-regMoney);
			userRepository.save(child);
			pointService.makePoint(child, admin, "정기 적금", regMoney);
			savingser.depositMoney(regMoney);
		}, new CronTrigger("0 0 0 " + dayDate + " * ?"));
		String endDate = savingser.getDate()
			.plus(financialProduct.getPeriod() + 1, ChronoUnit.MONTHS)
			.format(DateTimeFormatter.ofPattern("d M e"))
			.toString();
		endS.schedule(() -> {
			int money = savingser.getMoney();
			money = money + money * financialProduct.getRate() / 100;
			parent.moneyTransfer(-money);
			userRepository.save(parent);
			pointService.makePoint(parent, admin, "자녀의 만기 적금", money);
			child.moneyTransfer(money);
			userRepository.save(child);
			pointService.makePoint(admin, child, "적금 만기 금액", money);
			savingserRepository.delete(savingser);
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		savingserRepository.save(savingser);
	}

	@Override
	public void refuse(Long id) {
		savingserRepository.deleteById(id);
	}

	public List<SavingserDto> getSavingList(User user) {
		return savingserRepository.findByUser(user).stream().map(this::changeToDto)
			.collect(Collectors.toList());
	}

	private SavingserDto changeToDto(Savingser savingser) {
		FinancialProduct financialProduct = financialProductRepository.findById(
			savingser.getFinancialProduct().getId()).get();
		return SavingserDto.builder()
			.id(savingser.getId())
			.userId(savingser.getUser().getId())
			.money(savingser.getMoney())
			.date(savingser.getDate())
			.financialProductId(financialProduct.getId())
			.productName(financialProduct.getName())
			.regularMoney(savingser.getRegularMoney())
			.build();
	}
}

package com.woowahanbank.backend.domain.customer.service;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.customer.domain.Savingser;
import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.repository.SavingserRepository;
import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.financialproducts.repository.FinancialProductRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.UserDto;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SavingserServiceImpl implements CustomerService<SavingserDto> {
	private final SavingserRepository savingserRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;

	@Override
	public void apply(SavingserDto savingserDto, UserDto userDto) {
		User user = userRepository.findById(userDto.getId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingserDto.getFinancialProductId())
			.get();
		Savingser savingser = Savingser.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(savingserDto.getMoney())
			.regularMoney(savingserDto.getRegularMoney())
			.build();
		savingserRepository.save(savingser);
	}

	@Override
	public List<SavingserDto> getDisallow(UserDto userDto) {
		List<Savingser> list = savingserRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			userDto.getFamilyId());
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
		tpts.schedule(() -> {
			int regMoney = savingser.getRegularMoney();
			//사용자 돈 빼가기()
			savingser.depositMoney(regMoney);
		}, new CronTrigger("0 0 0 " + dayDate + " ?"));
		String endDate = savingser.getDate()
			.plus(financialProduct.getPeriod() + 1, ChronoUnit.DAYS)
			.format(DateTimeFormatter.ofPattern("d M e yyyy"))
			.toString();
		endS.schedule(() -> {
			//이자 계산
			//부모 돈 변경()
			//거래내역
			// 자식 금액 변경() - 모든 금액 반환
			// savingser 객체 삭제
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		savingserRepository.save(savingser);
	}

	@Override
	public void refuse(Long id) {
		savingserRepository.deleteById(id);
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
			.ProductName(financialProduct.getName())
			.regularMoney(savingser.getRegularMoney())
			.build();
	}
}

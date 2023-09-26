package com.woowahanbank.backend.domain.customer.service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	// private final ApplicationEventPublisher eventPublisher;
	DecimalFormat formatter = new DecimalFormat("###,###");

	@Override
	public void apply(LoanerDto loanerDto) {
		User user = userRepository.findById(loanerDto.getUserId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(loanerDto.getFinancialProductId())
			.get();
		User parent = userRepository.findById(financialProduct.getParent().getId()).get();
		Loaner loaner = Loaner.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(loanerDto.getMoney())
			.build();
		loanerRepository.save(loaner);
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, parent.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + financialProduct.getId()),
		// 	NotificationDto.builder()
		// 		.title("대출 상품 승인 신청")
		// 		.body(user.getNickname() + "님이 대출 상품 [" + financialProduct.getName()
		// 			+ "]을 금액 ( " + formatter.format(loanerDto.getMoney()) + " )원에 승인을 신청 했습니다.")
		// 		.build()
		// ));
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
	public void allow(Long id, User parent) {
		Loaner loaner = loanerRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(loaner.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		loaner.allow();
		loaner.changeDate();
		String cardNum = makeCardNumber(financialProduct.getFamily().getId(), financialProduct.getId(), parent.getId());
		loaner.makeCardNumber(cardNum);
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		ThreadPoolTaskScheduler endS = new ThreadPoolTaskScheduler();
		tpts.initialize();
		endS.initialize();
		String dayDate = loaner.getDate().format(DateTimeFormatter.ofPattern("d")).toString();
		User child = userRepository.findById(loaner.getUser().getId()).get();
		int loanMoney = loaner.getMoney();
		parent.moneyTransfer(-loanMoney);
		child.moneyTransfer(loanMoney);
		userRepository.save(parent);
		userRepository.save(child);
		tpts.schedule(() -> {
			int money = loaner.getMoney() * financialProduct.getRate() / 100; // 대출이자
			child.moneyTransfer(-money);
			userRepository.save(child);
			parent.moneyTransfer(money);
			userRepository.save(parent);
			pointService.makePoint(child, parent, "대출 이자", money);
		}, new CronTrigger("0 0 0 " + dayDate + " * ?"));
		String endDate = loaner.getDate()
			.plus(financialProduct.getPeriod(), ChronoUnit.MONTHS)
			.format(DateTimeFormatter.ofPattern("d M e"))
			.toString();
		endS.schedule(() -> {
			child.moneyTransfer(-loanMoney);
			userRepository.save(child);
			parent.moneyTransfer(loanMoney);
			userRepository.save(parent);
			pointService.makePoint(child, parent, "대출금 상환", loanMoney);
			loanerRepository.delete(loaner);
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		loanerRepository.save(loaner);
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, child.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/account"),
		// 	NotificationDto.builder()
		// 		.title("대출 상품 승인")
		// 		.body(parent.getNickname() + "님이 대출 상품 [" + financialProduct.getName()
		// 			+ "] 을 승인했습니다.")
		// 		.build()
		// ));
	}

	@Override
	public void refuse(Long id, User parent) {
		Loaner loaner = loanerRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(loaner.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		User child = userRepository.findById(loaner.getUser().getId()).get();
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, child.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + id),
		// 	NotificationDto.builder()
		// 		.title("대출 상품 거절")
		// 		.body(parent.getNickname() + "님이 대출 상품 [" + financialProduct.getName()
		// 			+ "] 을 거절했습니다.")
		// 		.build()
		// ));
		loanerRepository.deleteById(id);
	}

	@Override
	public List<LoanerDto> getDisallowProducts(Long productId) {
		List<Loaner> list = loanerRepository.findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(productId);
		List<LoanerDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	private String makeCardNumber(Long familyId, Long productId, Integer parentId) {
		Random random = new Random();
		StringBuilder sb;
		while (true) {
			int num = 0;
			int last = (int)(familyId % 10);
			while (familyId >= 10) {
				familyId /= 10;
				num++;
			}
			sb = new StringBuilder();
			sb.append(num + 1).append(familyId).append(last).append(productId % 10).append(parentId % 10).append(3);
			int sum = num + 1 + (int)(familyId % 10) * 2 + (int)(productId % 10) + (parentId % 10) * 2 + 3;
			for (int i = 0; i < 9; i++) {
				int randNum = random.nextInt(10);
				sb.append(randNum);
				if (i % 2 == 0)
					sum += randNum * 2;
				else
					sum += randNum;
			}
			sb.append(10 - (sum % 10));
			Loaner loan = loanerRepository.findByCardNumber(sb.toString());
			if (loan == null)
				break;
		}
		return sb.toString();
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
			.userNickname((loaner.getUser().getNickname()))
			.money(loaner.getMoney())
			.cardNumber(loaner.getCardNumber())
			.date(loaner.getDate())
			.financialProductId(financialProduct.getId())
			.productName(financialProduct.getName())
			.build();
	}
}

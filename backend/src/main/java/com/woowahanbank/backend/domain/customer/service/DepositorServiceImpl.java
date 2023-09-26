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

import com.woowahanbank.backend.domain.customer.domain.Depositor;
import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.repository.DepositorRepository;
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
public class DepositorServiceImpl implements CustomerService<DepositorDto> {
	private final DepositorRepository depositorRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;
	private final PointServiceImpl pointService;
	// private final ApplicationEventPublisher eventPublisher;
	DecimalFormat formatter = new DecimalFormat("###,###");

	@Override
	public void apply(DepositorDto depositorDto) {
		User user = userRepository.findById(depositorDto.getUserId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(depositorDto.getFinancialProductId())
			.get();
		User parent = userRepository.findById(financialProduct.getParent().getId()).get();
		Depositor depositor = Depositor.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(depositorDto.getMoney())
			.build();
		depositorRepository.save(depositor);
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, parent.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + financialProduct.getId()),
		// 	NotificationDto.builder()
		// 		.title("예금 상품 승인 신청")
		// 		.body(user.getNickname() + "님이 예금 상품 [" + financialProduct.getName()
		// 			+ "]을 ( " + formatter.format(depositorDto.getMoney()) + " )원 금액에 승인을 신청 했습니다.")
		// 		.build()
		// ));
	}

	@Override
	public List<DepositorDto> getDisallow(CustomUserDetails customUser) {
		List<Depositor> list = depositorRepository.findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(
			customUser.getUser().getFamily().getId());
		List<DepositorDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	@Override
	public void allow(Long id, User parent) {
		Depositor depositor = depositorRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(depositor.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		depositor.allow();
		depositor.changeDate();
		String cardNum = makeCardNumber(financialProduct.getFamily().getId(), financialProduct.getId(), parent.getId());
		depositor.makeCardNumber(cardNum);
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		ThreadPoolTaskScheduler endS = new ThreadPoolTaskScheduler();
		tpts.initialize();
		endS.initialize();
		String dayDate = depositor.getDate().format(DateTimeFormatter.ofPattern("d")).toString();
		User admin = userRepository.findById(1).get(); // 가상의 admin 유저
		User child = userRepository.findById(depositor.getUser().getId()).get();
		int dMoney = depositor.getMoney();
		child.moneyTransfer(dMoney);
		userRepository.save(child);
		tpts.schedule(() -> {
			int money = depositor.getMoney() * financialProduct.getRate() / 100; // 이자
			depositor.depositMoney(money);
			parent.moneyTransfer(-money);
			userRepository.save(parent);
			pointService.makePoint(parent, admin, "예금 이자", money);
		}, new CronTrigger("0 0 0 " + dayDate + " * ?"));
		String endDate = depositor.getDate()
			.plus(financialProduct.getPeriod(), ChronoUnit.MONTHS)
			.format(DateTimeFormatter.ofPattern("d M e"))
			.toString();
		endS.schedule(() -> {
			int money = depositor.getMoney();
			child.moneyTransfer(money);
			userRepository.save(child);
			pointService.makePoint(admin, child, "예금 만기", money);
			depositorRepository.delete(depositor);
			tpts.shutdown();
			endS.shutdown();
		}, new CronTrigger("0 0 0 " + endDate));
		depositorRepository.save(depositor);
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, child.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/account"),
		// 	NotificationDto.builder()
		// 		.title("예금 상품 승인")
		// 		.body(parent.getNickname() + "님이 예금 상품 [" + financialProduct.getName()
		// 			+ "] 을 승인했습니다.")
		// 		.build()
		// ));
	}

	@Override
	public void refuse(Long id, User parent) {
		Depositor depositor = depositorRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(depositor.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		User child = userRepository.findById(depositor.getUser().getId()).get();
		// eventPublisher.publishEvent(new NotificationEvent(
		// 	this, child.getNickname(),
		// 	NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + id),
		// 	NotificationDto.builder()
		// 		.title("예금 상품 거절")
		// 		.body(parent.getNickname() + "님이 예금 상품 [" + financialProduct.getName()
		// 			+ "] 을 거절했습니다.")
		// 		.build()
		// ));
		depositorRepository.deleteById(id);
	}

	@Override
	public List<DepositorDto> getDisallowProducts(Long productId) {
		List<Depositor> list = depositorRepository.findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(
			productId);
		List<DepositorDto> res = new ArrayList<>();
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
			sb.append(num + 1).append(familyId).append(last).append(productId % 10).append(parentId % 10).append(1);
			int sum = num + 1 + (int)(familyId % 10) * 2 + (int)(productId % 10) + (parentId % 10) * 2 + 1;
			for (int i = 0; i < 9; i++) {
				int randNum = random.nextInt(10);
				sb.append(randNum);
				if (i % 2 == 0)
					sum += randNum * 2;
				else
					sum += randNum;
			}
			sb.append(10 - (sum % 10));
			Depositor depo = depositorRepository.findByCardNumber(sb.toString());
			if (depo == null)
				break;
		}
		return sb.toString();
	}

	public List<DepositorDto> getDepositorList(User user) {
		return depositorRepository.findByUser(user).stream().map(this::changeToDto)
			.collect(Collectors.toList());
	}

	private DepositorDto changeToDto(Depositor depositor) {
		FinancialProduct financialProduct = financialProductRepository.findById(
			depositor.getFinancialProduct().getId()).get();
		return DepositorDto.builder()
			.id(depositor.getId())
			.userId(depositor.getUser().getId())
			.userNickname((depositor.getUser().getNickname()))
			.money(depositor.getMoney())
			.cardNumber(depositor.getCardNumber())
			.date(depositor.getDate())
			.financialProductId(financialProduct.getId())
			.productName(financialProduct.getName())
			.build();
	}

}

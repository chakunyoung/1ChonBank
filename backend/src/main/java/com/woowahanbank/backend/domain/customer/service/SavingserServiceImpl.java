package com.woowahanbank.backend.domain.customer.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
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
import com.woowahanbank.backend.global.notification.dto.NotificationDto;
import com.woowahanbank.backend.global.notification.event.NotificationEvent;
import com.woowahanbank.backend.global.util.NotificationUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SavingserServiceImpl implements CustomerService<SavingserDto> {
	private final SavingserRepository savingserRepository;
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;
	private final PointServiceImpl pointService;
	private final ApplicationEventPublisher eventPublisher;
	DecimalFormat formatter = new DecimalFormat("###,###");

	@Override
	public void apply(SavingserDto savingserDto) {
		User user = userRepository.findById(savingserDto.getUserId()).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingserDto.getFinancialProductId())
			.get();
		User parent = userRepository.findById(financialProduct.getParent().getId()).get();
		Savingser savingser = Savingser.builder()
			.user(user)
			.financialProduct(financialProduct)
			.allowProduct(false)
			.money(0)
			.regularMoney(savingserDto.getRegularMoney())
			.build();
		savingserRepository.save(savingser);
		eventPublisher.publishEvent(new NotificationEvent(
			this, parent.getNickname(),
			NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + financialProduct.getId()),
			NotificationDto.builder()
				.title("적금 상품 승인 신청")
				.body(user.getNickname() + "님이 적금 상품 [" + financialProduct.getName()
					+ "]을 금액 ( " + formatter.format(savingserDto.getMoney()) + " )원 에 승인을 신청 했습니다.")
				.build()
		));
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
	public void allow(Long id, User parent) {
		Savingser savingser = savingserRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingser.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		savingser.allow();
		savingser.changeDate(financialProduct.getPeriod());
		String cardNum = makeCardNumber(financialProduct.getFamily().getId(), financialProduct.getId(), parent.getId());
		savingser.makeCardNumber(cardNum);
		User admin = userRepository.findById(1).get(); // 가상의 admin 유저
		User child = userRepository.findById(savingser.getUser().getId()).get();
		int money = savingser.getRegularMoney();
		child.moneyTransfer(-money);
		userRepository.save(child);
		savingserRepository.save(savingser);
		pointService.makePoint(child, admin, "정기 적금", money);
		eventPublisher.publishEvent(new NotificationEvent(
			this, child.getNickname(),
			NotificationUtil.clickUrl("http://localhost:3000/account"),
			NotificationDto.builder()
				.title("예금 상품 승인")
				.body(parent.getNickname() + "님이 예금 상품 [" + financialProduct.getName()
					+ "] 을 승인했습니다.")
				.build()
		));
	}

	@Override
	public void refuse(Long id, User parent) {
		Savingser savingser = savingserRepository.findById(id).get();
		FinancialProduct financialProduct = financialProductRepository.findById(savingser.getFinancialProduct().getId())
			.get();
		if (parent.getFamily().getId() != financialProduct.getFamily().getId())
			throw new IllegalArgumentException("해당 가족이 아닙니다.");
		User child = userRepository.findById(savingser.getUser().getId()).get();
		eventPublisher.publishEvent(new NotificationEvent(
			this, child.getNickname(),
			NotificationUtil.clickUrl("http://localhost:3000/financeDetail/" + id),
			NotificationDto.builder()
				.title("적금 상품 거절")
				.body(parent.getNickname() + "님이 적금 상품 [" + financialProduct.getName()
					+ "] 을 거절했습니다.")
				.build()
		));
		savingserRepository.deleteById(id);
	}

	@Override
	public List<SavingserDto> getDisallowProducts(Long productId) {
		List<Savingser> list = savingserRepository.findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(
			productId);
		List<SavingserDto> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(changeToDto(list.get(i)));
		}
		return res;
	}

	@Override
	public void calculateRates() {
		int today = LocalDateTime.now().getDayOfMonth();
		List<Savingser> list = savingserRepository.findByDate_Date(today);
		User admin = userRepository.findById(2).get(); // 가상의 적금 유저
		for (int i = 0; i < list.size(); i++) {
			Savingser savingser = list.get(i);
			User child = userRepository.findById(savingser.getUser().getId()).get();
			int regMoney = savingser.getRegularMoney();
			child.moneyTransfer(-regMoney);
			savingser.depositMoney(regMoney);
			userRepository.save(child);
			savingserRepository.save(savingser);
			pointService.makePoint(child, admin, "정기 적금", regMoney);
		}
	}

	@Override
	public void removeProduct() {
		List<Savingser> list = savingserRepository.findByExpiryAfter(LocalDateTime.now());
		User admin = userRepository.findById(2).get(); // 가상의 적금 유저
		for (int i = 0; i < list.size(); i++) {
			Savingser savi = list.get(i);
			FinancialProduct financialProduct = financialProductRepository.findById(savi.getFinancialProduct().getId())
				.get();
			int money = savi.getMoney();
			money += (money * financialProduct.getRate()) / 100;
			User child = userRepository.findById(savi.getUser().getId()).get();
			child.moneyTransfer(money);
			userRepository.save(child);
			savingserRepository.delete(savi);
			pointService.makePoint(admin, child, "적금 만기", money);
		}
	}

	@Override
	public List<SavingserDto> getProductsByNickname(String nickname) {
		User user = userRepository.findByNickname(nickname).get();
		List<Savingser> list = savingserRepository.findByUser(user);
		List<SavingserDto> res = new ArrayList<>();
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
			sb.append(num + 1).append(familyId).append(last).append(productId % 10).append(parentId % 10).append(2);
			int sum = num + 1 + (int)(familyId % 10) * 2 + (int)(productId % 10) + (parentId % 10) * 2 + 2;
			for (int i = 0; i < 9; i++) {
				int randNum = random.nextInt(10);
				sb.append(randNum);
				if (i % 2 == 0)
					sum += randNum * 2;
				else
					sum += randNum;
			}
			sb.append(10 - (sum % 10));
			Savingser sav = savingserRepository.findByCardNumber(sb.toString());
			if (sav == null)
				break;
		}
		return sb.toString();
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
			.userNickname(savingser.getUser().getNickname())
			.money(savingser.getMoney())
			.cardNumber(savingser.getCardNumber())
			.date(savingser.getDate())
			.expiry(savingser.getExpiry() == null ? "" :
				savingser.getExpiry().format(DateTimeFormatter.ofPattern("YMMdd")).toString())
			.financialProductId(financialProduct.getId())
			.productName(financialProduct.getName())
			.regularMoney(savingser.getRegularMoney())
			.build();
	}
}

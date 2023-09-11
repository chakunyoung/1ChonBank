package com.woowahanbank.backend.domain.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.banking.domain.AccountTransaction;
import com.woowahanbank.backend.domain.banking.repository.BankingRepository;
import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.service.DepositorServiceImpl;
import com.woowahanbank.backend.domain.customer.service.LoanerServiceImpl;
import com.woowahanbank.backend.domain.customer.service.SavingserServiceImpl;
import com.woowahanbank.backend.domain.user.domain.Role;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import com.woowahanbank.backend.global.exception.custom.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankingService {

	private final BankingRepository bankingRepository;
	private final UserRepository userRepository;

	private final DepositorServiceImpl depositorService;
	private final LoanerServiceImpl loanerService;
	private final SavingserServiceImpl savingserService;

	public List<AccountTransaction> getAccountTransactions(User user) {
		return bankingRepository.findBySenderOrReceiverOrderByCreatedAtAsc(user, user);
	}

	public void pointTransfer(User user, long amount, Role requiredRole) {
		if (user.getRoles() != requiredRole) {
			throw new ForbiddenException("접근이 거부되었습니다: 권한이 없습니다.");
		}
		user.moneyTransfer(amount);
		userRepository.save(user);
	}

	public List<DepositorDto> getDepositorList(User user) {
		return depositorService.getDepositorList(user);
	}

	public List<LoanerDto> getLoanerList(User user) {
		return loanerService.getLoanerList(user);
	}

	public List<SavingserDto> getSavingList(User user) {
		return savingserService.getSavingList(user);
	}

}

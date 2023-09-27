package com.woowahanbank.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.woowahanbank.backend.domain.banking.service.BankingService;
import com.woowahanbank.backend.domain.customer.dto.DepositorDto;
import com.woowahanbank.backend.domain.customer.dto.LoanerDto;
import com.woowahanbank.backend.domain.customer.dto.SavingserDto;
import com.woowahanbank.backend.domain.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {

	private final BankingService bankingService;

	private final CustomerService<DepositorDto> depositorService;
	private final CustomerService<LoanerDto> loanerService;
	private final CustomerService<SavingserDto> savingserService;

	@Scheduled(cron = "0 0 6 * * ?")
	public void runAtSixAM() {
		bankingService.updatePinMoneyAndTransfer();
	}

	@Scheduled(cron = "0 0 2 * * ?")
	public void removeProduct() {
		depositorService.removeProduct();
		loanerService.removeProduct();
		savingserService.removeProduct();
	}

	@Scheduled(cron = "0 0 4 * * ?")
	public void calculateRates() {
		depositorService.calculateRates();
		loanerService.calculateRates();
		savingserService.calculateRates();
	}
}
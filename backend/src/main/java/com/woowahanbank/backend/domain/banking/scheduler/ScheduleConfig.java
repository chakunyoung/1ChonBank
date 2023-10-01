package com.woowahanbank.backend.domain.banking.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.woowahanbank.backend.domain.banking.service.BankingService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {

	private final BankingService bankingService;

	@Scheduled(cron = "0 0 6 * * ?")
	public void runAtSixAM() {
		bankingService.updatePinMoneyAndTransfer();
	}
}

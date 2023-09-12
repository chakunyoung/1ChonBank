package com.woowahanbank.backend.domain.customer.service;

public interface DepositMoneyService {
	void depositMoney(int money, Long depositorId, int id);
}

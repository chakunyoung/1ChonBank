package com.woowahanbank.backend.domain.banking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.woowahanbank.backend.domain.banking.domain.PinMoney;

public interface PinMoneyRepository extends JpaRepository<PinMoney, Long> {
	@Query("SELECT p FROM PinMoney p WHERE DATE(p.receiveTime) = :today")
	List<PinMoney> findAllByReceiveTime(LocalDate today);
}

package com.woowahanbank.backend.domain.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.banking.domain.AccountTransaction;
import com.woowahanbank.backend.domain.user.domain.User;

public interface BankingRepository extends JpaRepository<AccountTransaction, Long> {
	List<AccountTransaction> findByReceiver(User receiver);

	List<AccountTransaction> findBySender(User sender);

	List<AccountTransaction> findBySenderOrReceiverOrderByCreatedAtAsc(User sender, User receiver);
}

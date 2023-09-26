package com.woowahanbank.backend.domain.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.customer.domain.Savingser;
import com.woowahanbank.backend.domain.user.domain.User;

public interface SavingserRepository extends JpaRepository<Savingser, Long> {
	List<Savingser> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);

	List<Savingser> findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(Long productId);

	List<Savingser> findByUser(User user);

	Savingser findByCardNumber(String cardNumber);
}

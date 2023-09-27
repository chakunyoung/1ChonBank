package com.woowahanbank.backend.domain.customer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woowahanbank.backend.domain.customer.domain.Savingser;
import com.woowahanbank.backend.domain.user.domain.User;

public interface SavingserRepository extends JpaRepository<Savingser, Long> {
	List<Savingser> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);

	List<Savingser> findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(Long productId);

	List<Savingser> findByUser(User user);

	Savingser findByCardNumber(String cardNumber);

	@Query("SELECT s FROM Loaner s WHERE DAY(s.date) = :targetDate")
	List<Savingser> findByDate_Date(@Param("targetDate") int targetDay);

	List<Savingser> findByExpiryAfter(LocalDateTime today);
}

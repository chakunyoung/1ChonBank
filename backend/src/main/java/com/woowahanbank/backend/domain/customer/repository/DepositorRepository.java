package com.woowahanbank.backend.domain.customer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woowahanbank.backend.domain.customer.domain.Depositor;
import com.woowahanbank.backend.domain.user.domain.User;

public interface DepositorRepository extends JpaRepository<Depositor, Long> {
	List<Depositor> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);

	List<Depositor> findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(Long productId);

	List<Depositor> findByUser(User user);

	Depositor findByCardNumber(String str);

	@Query("SELECT d FROM Depositor d WHERE DAY(d.date) = :targetDate")
	List<Depositor> findByDate_Date(@Param("targetDate") int targetDay);

	List<Depositor> findByExpiryAfter(LocalDateTime today);
}

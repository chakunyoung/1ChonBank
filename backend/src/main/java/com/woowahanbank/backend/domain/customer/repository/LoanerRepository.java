package com.woowahanbank.backend.domain.customer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woowahanbank.backend.domain.customer.domain.Loaner;
import com.woowahanbank.backend.domain.user.domain.User;

public interface LoanerRepository extends JpaRepository<Loaner, Long> {
	List<Loaner> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);

	List<Loaner> findAllByFinancialProduct_IdAndAllowProductIsFalseOrderByIdDesc(Long productId);

	List<Loaner> findByUser(User user);

	Loaner findByCardNumber(String str);

	@Query("SELECT l FROM Loaner l WHERE DAY(l.date) = :targetDate")
	List<Loaner> findByDate_Date(@Param("targetDate") int targetDay);

	List<Loaner> findByExpiryAfter(LocalDateTime today);
}

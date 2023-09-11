package com.woowahanbank.backend.domain.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.customer.domain.Loaner;
import com.woowahanbank.backend.domain.user.domain.User;

public interface LoanerRepository extends JpaRepository<Loaner, Long> {
	List<Loaner> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);

	List<Loaner> findByUser(User user);
}

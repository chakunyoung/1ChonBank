package com.woowahanbank.backend.domain.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.customer.domain.Savingser;

public interface SavingserRepository extends JpaRepository<Savingser, Long> {
	List<Savingser> findAllByUser_FamilyIdAndAllowProductIsFalseOrderByIdDesc(Long familyId);
}

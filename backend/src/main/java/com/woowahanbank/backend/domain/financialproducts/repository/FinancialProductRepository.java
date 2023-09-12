package com.woowahanbank.backend.domain.financialproducts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;

public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long> {
	List<FinancialProduct> findAllByFamily_IdOrderByIdDesc(Long familyId);

	List<FinancialProduct> findAllByFamily_IdAndProductTypeOrderByIdDesc(Long familyId, String productType);
}

package com.woowahanbank.backend.domain.financialproducts.service;

import java.util.List;

import com.woowahanbank.backend.domain.financialproducts.dto.FinancialProductsDto;

public interface FinancialProductsService {
	void registerFinancialProducts(FinancialProductsDto dto);

	List<FinancialProductsDto> getFinancialProductDtoList(Long familyId);

	List<FinancialProductsDto> getFinancialSubProductDtoList(Long familyId, String productType);
}

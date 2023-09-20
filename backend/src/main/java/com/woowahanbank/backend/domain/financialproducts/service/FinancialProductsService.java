package com.woowahanbank.backend.domain.financialproducts.service;

import java.util.List;

import com.woowahanbank.backend.domain.financialproducts.dto.FinancialProductsDto;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

public interface FinancialProductsService {
	void registerFinancialProducts(FinancialProductsDto dto);

	List<FinancialProductsDto> getFinancialProductDtoList(CustomUserDetails customUser);

	List<FinancialProductsDto> getFinancialSubProductDtoList(Long familyId, String productType);
}

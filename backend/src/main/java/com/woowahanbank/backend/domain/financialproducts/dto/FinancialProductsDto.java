package com.woowahanbank.backend.domain.financialproducts.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.woowahanbank.backend.domain.financialproducts.domain.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialProductsDto {
	private Long id;
	private Long parentId;
	private Long familyId;
	private String name;
	private int rate;
	private String info;
	private int period;
	@Enumerated(EnumType.STRING)
	private ProductType productType;
}

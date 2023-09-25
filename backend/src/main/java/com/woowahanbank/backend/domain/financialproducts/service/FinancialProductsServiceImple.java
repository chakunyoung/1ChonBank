package com.woowahanbank.backend.domain.financialproducts.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.family.domain.Family;
import com.woowahanbank.backend.domain.family.repository.FamilyRepository;
import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.financialproducts.dto.FinancialProductsDto;
import com.woowahanbank.backend.domain.financialproducts.repository.FinancialProductRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FinancialProductsServiceImple implements FinancialProductsService {
	private final FinancialProductRepository financialProductRepository;
	private final UserRepository userRepository;
	private final FamilyRepository familyRepository;

	@Override
	public void registerFinancialProducts(FinancialProductsDto dto) {
		financialProductRepository.save(changeToEntity(dto));
	}

	@Override
	public List<FinancialProductsDto> getFinancialProductDtoList(CustomUserDetails customUser) {
		long familyId = customUser.getUser().getFamily().getId();
		List<FinancialProduct> entityList = financialProductRepository.findAllByFamily_IdOrderByIdDesc(familyId);
		List<FinancialProductsDto> dtoList = new ArrayList<>();
		for (int i = 0; i < entityList.size(); i++) {
			dtoList.add(changeToDto(entityList.get(i)));
		}
		return dtoList;
	}

	@Override
	public FinancialProductsDto getFinancialInfo(Long productId) {
		return changeToDto(financialProductRepository.findById(productId).get());
	}

	private FinancialProductsDto changeToDto(FinancialProduct financialProduct) {
		return FinancialProductsDto.builder()
			.id(financialProduct.getId())
			.parentId(financialProduct.getParent().getId())
			.familyId(financialProduct.getFamily().getId())
			.name(financialProduct.getName())
			.rate(financialProduct.getRate())
			.info(financialProduct.getInfo())
			.period(financialProduct.getPeriod())
			.productType(financialProduct.getProductType())
			.build();
	}

	private FinancialProduct changeToEntity(FinancialProductsDto dto) {
		User parent = userRepository.findById(dto.getParentId()).get();
		Family family = familyRepository.findById(dto.getFamilyId()).get();
		return FinancialProduct.builder()
			.parent(parent)
			.family(family)
			.name(dto.getName())
			.rate(dto.getRate())
			.info(dto.getInfo())
			.period(dto.getPeriod())
			.productType(dto.getProductType())
			.build();
	}
}

package com.woowahanbank.backend.domain.financialproducts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.financialproducts.dto.FinancialProductsDto;
import com.woowahanbank.backend.domain.financialproducts.service.FinancialProductsService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"FinancialProducts API"})
@RequestMapping("/api/financial")
public class FinancialProductsController {
	private final FinancialProductsService financialProductsService;

	@ApiOperation(value = "금융 상품 등록")
	@ApiResponse(code = 200, message = "금융상품 등록 성공")
	@PostMapping
	public ResponseEntity<?> registerFinancialProducts(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody FinancialProductsDto dto) {
		dto.setFamilyId(customUser.getUser().getFamily().getId());
		dto.setParentId(customUser.getUser().getId());
		try {
			financialProductsService.registerFinancialProducts(dto);
			return BaseResponse.ok(HttpStatus.OK, "금융상품 등록 성공");
		} catch (Exception e) {
			return BaseResponse.fail("금융상품 등록 실패", 400);
		}
	}

	@ApiOperation(value = "우리 가족 모든 금융 상품 조회")
	@ApiResponse(code = 200, message = "우리 가족의 모든 금융 상품")
	@GetMapping
	public ResponseEntity<?> getAllProductList(@AuthenticationPrincipal CustomUserDetails customUser) {
		List<FinancialProductsDto> productDtoList = financialProductsService.getFinancialProductDtoList(customUser);
		return BaseResponse.okWithData(HttpStatus.OK, "우리 가족의 모든 금융 상품", productDtoList);
	}

	@ApiOperation(value = "특정 금융 상품 조회")
	@ApiImplicitParam(name = "productId", value = "상품Id", required = true, dataType = "Long", paramType = "path")
	@ApiResponse(code = 200, message = "특정 금융 상품 정보")
	@GetMapping("/{productId}")
	public ResponseEntity<?> getSubProductList(@AuthenticationPrincipal CustomUserDetails customUser,
		@PathVariable Long productId) {
		FinancialProductsDto productDto = financialProductsService.getFinancialInfo(productId);
		if (productDto.getFamilyId() != customUser.getUser().getFamily().getId()) {
			productDto = null;
		}
		return BaseResponse.okWithData(HttpStatus.OK, "특정 금융 상품 정보", productDto);
	}
}

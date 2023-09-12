package com.woowahanbank.backend.domain.customer.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanerDto {
	private Long id;
	private Integer userId;
	private Long financialProductId;
	private String ProductName;
	private Boolean grant;
	private int money;
	private LocalDateTime date;
}

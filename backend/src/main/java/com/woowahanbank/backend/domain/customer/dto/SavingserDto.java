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
public class SavingserDto {
	private Long id;
	private Integer userId;
	private String userNickname;
	private Long financialProductId;
	private String productName;
	private Boolean grant;
	private int money;
	private String cardNumber;
	private LocalDateTime date;
	private int regularMoney;
}

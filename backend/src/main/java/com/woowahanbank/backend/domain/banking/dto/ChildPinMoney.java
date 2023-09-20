package com.woowahanbank.backend.domain.banking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ChildPinMoney {
	private String childNickname;
	private int pinMoney;
	private LocalDate receiveTime;
}

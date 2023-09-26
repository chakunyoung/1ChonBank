package com.woowahanbank.backend.domain.customer.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.woowahanbank.backend.domain.financialproducts.domain.FinancialProduct;
import com.woowahanbank.backend.domain.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Depositor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "financialProduct_id")
	private FinancialProduct financialProduct;
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean allowProduct;
	private int money;
	private String cardNumber;
	@CreationTimestamp
	private LocalDateTime date;

	public void allow() {
		if (this.allowProduct)
			throw new IllegalArgumentException("이미 허가된 상품입니다.");
		this.allowProduct = true;
	}

	public void depositMoney(int userMoney) {
		this.money += userMoney;
	}

	public void changeDate() {
		this.date = LocalDateTime.now();
	}

	public void makeCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}

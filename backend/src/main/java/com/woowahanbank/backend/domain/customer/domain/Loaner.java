// package com.woowahanbank.backend.domain.customer.domain;
//
// import java.time.LocalDateTime;
//
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
//
// import org.hibernate.annotations.CreationTimestamp;
//
// import com.woowahanbank.backend.domain.financialproducts.domain.Loan;
// import com.woowahanbank.backend.domain.user.domain.User;
//
// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Entity
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor
// @Getter
// @Builder
// public class Loaner {
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	private Long id;
// 	@ManyToOne
// 	@JoinColumn(name = "user_id")
// 	private User user;
// 	@ManyToOne
// 	@JoinColumn(name = "loan_id")
// 	private Loan loan;
// 	private Boolean grant;
// 	private int money;
// 	@CreationTimestamp
// 	private LocalDateTime date;
//
// }

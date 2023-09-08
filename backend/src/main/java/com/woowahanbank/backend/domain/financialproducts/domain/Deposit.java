// package com.woowahanbank.backend.domain.financialproducts.domain;
//
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
//
// // import com.woowahanbank.backend.domain.family.domain.Family;
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
// public class Deposit {
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	private Long id;
// 	// @ManyToOne
// 	// @JoinColumn(name = "family_id")
// 	// private Family family;
// 	private String name;
// 	private int rate;
// 	private String info;
// 	private int period;
// }

package com.woowahanbank.backend.domain.point.dto;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.woowahanbank.backend.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
	private Long id;
	private String senderNickname;
	private String receiverNickname;
	private int amount;
	private String memo;
	private LocalDateTime time;
}

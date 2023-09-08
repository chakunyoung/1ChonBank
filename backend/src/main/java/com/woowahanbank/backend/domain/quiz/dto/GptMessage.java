package com.woowahanbank.backend.domain.quiz.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GptMessage {
	private String role;
	private String content;
}

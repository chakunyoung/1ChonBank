package com.woowahanbank.backend.domain.quiz.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GptResponseDto {
	private String content;
}

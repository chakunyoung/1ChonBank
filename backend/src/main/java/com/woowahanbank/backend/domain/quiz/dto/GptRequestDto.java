package com.woowahanbank.backend.domain.quiz.dto;

import java.util.List;

import lombok.*;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GptRequestDto {
	private String model;
	private List<GptMessage> messages;
}

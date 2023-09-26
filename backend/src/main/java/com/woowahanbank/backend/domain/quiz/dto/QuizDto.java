package com.woowahanbank.backend.domain.quiz.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QuizDto {

	private Long quizId;
	private String quizDetail;
	private String quizAnswer;

}
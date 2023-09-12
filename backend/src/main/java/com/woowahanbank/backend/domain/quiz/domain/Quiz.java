package com.woowahanbank.backend.domain.quiz.domain;

import javax.persistence.*;

import com.sun.istack.NotNull;
import com.woowahanbank.backend.domain.quiz.dto.QuizDto;

import lombok.*;

@Entity
@Table(name = "quiz")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "quiz_choice1", length = 1000)
	private String quizChoice1;
	@Column(name = "quiz_choice2", length = 1000)
	private String quizChoice2;
	@Column(name = "quiz_choice3", length = 1000)
	private String quizChoice3;
	@Column(name = "quiz_choice4", length = 1000)
	private String quizChoice4;
	@Column(name = "quiz_choice5", length = 1000)
	private String quizChoice5;
	@Column(name = "quiz_answer", length = 1000)
	private String quizAnswer;
	@Column(name = "quiz_commentary", length = 1000)
	private String quizCommentary;
	@Column(name = "quiz_question", length = 1000)
	private String quizQuestion;

	// @NotNull
	// @Column(name = "quiz_detail", length = 3000)
	// private String quizDetail;
	// private void saveQuiz(QuizDto quizDto) {
	// 	//this.quizId = quizDto.getQuizId();
	// 	this.quizDetail = quizDto.getQuizDetail();
	// 	this.quizAnswer = quizDto.getQuizAnswer();
	// }

}

package com.woowahanbank.backend.domain.quiz.domain;

import javax.persistence.*;
import com.sun.istack.NotNull;
import lombok.*;

@Entity
@Table(name="quiz")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long quizId;

	@NotNull
	@Column(name="quiz_detail")
	private String quizDetail;

	@NotNull
	@Column(name="quiz_answer")
	private String quizAnswer;


}

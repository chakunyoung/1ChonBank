package com.woowahanbank.backend.domain.quiz.dto;

import javax.persistence.*;
import lombok.*;


public class Quiz {

	private int quizId;

	private String quizDetails;

	private String quizAnswer;

	public Quiz() {
		super();
	}

	public Quiz(int quizId, String quizDetails, String quizAnswer) {
		this.quizId = quizId;
		this.quizDetails = quizDetails;
		this.quizAnswer = quizAnswer;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public void setQuizDetails(String quizDetails) {
		this.quizDetails = quizDetails;
	}

	public void setQuizAnswer(String quizAnswer) {
		this.quizAnswer = quizAnswer;
	}

	public int getQuizId() {
		return quizId;
	}

	public String getQuizDetails() {
		return quizDetails;
	}

	public String getQuizAnswer() {
		return quizAnswer;
	}



	@java.lang.Override
	public java.lang.String toString() {
		return "Quiz{" +
			"quizId=" + quizId +
			", quizDetails='" + quizDetails + '\'' +
			", quizAnswer='" + quizAnswer + '\'' +
			'}';
	}
}

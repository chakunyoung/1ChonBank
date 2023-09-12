package com.woowahanbank.backend.domain.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.woowahanbank.backend.domain.quiz.domain.Quiz;
import com.woowahanbank.backend.domain.quiz.service.QuizService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

	private final QuizService quizService;

	@Autowired
	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/todayQuiz")
	public ResponseEntity<Quiz> getQuiz() {
		Optional<Quiz> todayQuiz = quizService.findTodayQuiz();
		return todayQuiz.map(quiz -> ResponseEntity.ok().body(quiz))
			.orElseGet(() -> ResponseEntity.notFound().build());
	}
	@PostMapping("/make")
	public ResponseEntity<String> makeQuiz() {
		quizService.makeQuiz();
		return ResponseEntity.ok("Quiz 생성 요청이 수행되었습니다.");
	}

}

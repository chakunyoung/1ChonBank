package com.woowahanbank.backend.domain.quiz.controller;

import java.util.Optional;

import com.woowahanbank.backend.domain.quiz.dto.QuizPoint;
import com.woowahanbank.backend.global.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.woowahanbank.backend.domain.quiz.domain.Quiz;
import com.woowahanbank.backend.domain.quiz.service.QuizService;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

	private final QuizService quizService;

	@Autowired
	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/todayQuiz")
	public ResponseEntity<Quiz> getQuiz(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		Optional<Quiz> todayQuiz = quizService.findTodayQuiz();
		return todayQuiz.map(quiz -> ResponseEntity.ok().body(quiz))
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/make")
	public ResponseEntity<String> makeQuiz() {
		quizService.makeQuiz();
		return ResponseEntity.ok("Quiz 생성 요청이 수행되었습니다.");
	}

	@PutMapping("/updatePoint")
	public ResponseEntity<?> updatePoint(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody QuizPoint quizPoint){
		User user = customUserDetails.getUser();
		Long point = Long.valueOf(quizPoint.getQuizPoint());
		quizService.saveQuizPoint(user, point);
		return BaseResponse.ok(HttpStatus.OK, "포인트 업데이트 됨");
	}



	@DeleteMapping("/deleteQuiz")
	public ResponseEntity<String> deleteQuiz() {
		quizService.deleteQuiz();
		return ResponseEntity.ok("Quiz를 전부 삭제 했습니다.");
	}

	@PostMapping("/solved")
	public ResponseEntity<String> solvedQuiz(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		User user = customUserDetails.getUser();
		quizService.solvedQuiz(user);
		return ResponseEntity.ok("Quiz를 푸셨습니다.");
	}

}

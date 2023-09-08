package com.woowahanbank.backend.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanbank.backend.domain.quiz.domain.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


	// 조건에 따라 Quiz 객체 조회
	Optional<Quiz> findByQuizDetail(String quizDetail);


}

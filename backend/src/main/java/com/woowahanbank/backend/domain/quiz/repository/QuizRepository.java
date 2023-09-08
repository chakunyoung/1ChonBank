package com.woowahanbank.backend.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanbank.backend.domain.quiz.domain.Quiz;

import java.util.Optional;

import antlr.collections.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	Quiz save(Quiz quiz);
	Optional<Quiz> findById(Long id);


	Optional<Quiz> findById();

	// 조건에 따라 Quiz 객체 조회
	Optional<Quiz> findByQuizDetail(String quizDetail);

	// ID를 기반으로 Quiz 객체 삭제 (Delete)
	void deleteById(Long id);


}

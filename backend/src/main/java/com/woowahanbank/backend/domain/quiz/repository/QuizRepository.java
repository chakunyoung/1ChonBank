package com.woowahanbank.backend.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanbank.backend.domain.quiz.domain.Quiz;
import com.woowahanbank.backend.domain.quiz.dto.QuizDto;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


}

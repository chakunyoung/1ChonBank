package com.woowahanbank.backend.domain.quiz.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanbank.backend.domain.quiz.domain.Quiz;
import com.woowahanbank.backend.domain.quiz.dto.GptMessage;
import com.woowahanbank.backend.domain.quiz.dto.GptRequestDto;
import com.woowahanbank.backend.domain.quiz.dto.GptResponseDto;
import com.woowahanbank.backend.domain.quiz.dto.QuizDto;
import com.woowahanbank.backend.domain.quiz.repository.QuizRepository;
import com.woowahanbank.backend.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

	@Component
	public class MyScheduler {
		@Scheduled(cron = "0 0 12 * * ?") // 매일 12시에 실행
		public void scheduledTask() {
			// deleteQuiz();
			// chatGpt();
		}
	}

	private static final String GPT_URL = "https://api.openai.com/v1/chat/completions";
	private RestTemplate restTemplate;
	private HttpHeaders headers;

	private final QuizRepository quizRepository;

	public void chatGpt(@Value("${gpt.key}") String key) {
		this.restTemplate = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(JwtTokenUtil.HEADER_STRING, JwtTokenUtil.TOKEN_PREFIX + key);
	}

	public ResponseEntity<String> createQuestionsBasedOnIntro(Map<String, String> body) {
		log.info("AI 질문 Generate!");
		try {

			Map<String, String> gptMessage = new LinkedHashMap<>();
			gptMessage.put("role", "system");
			gptMessage.put("content", "너는 똑똑한 경제 선생님이야, 쉬운 문제 중심으로 금융 문제 1개를 5지 선다로 내어줘"
				+ " 항상 아래와 같은 형식을 맞추어서 대답해줘 "
				+ "문제. (문제)?\n"
				+ "1번 (선지).\n"
				+ "2번 (선지).\n"
				+ "3번 (선지).\n"
				+ "4번 (선지).\n"
				+ "5번 (선지).\n"
			);

			GptRequestDto gptRequestDto = GptRequestDto.builder()
				.model("gpt-3.5-turbo")
				.messages(List.of(new GptMessage("system", "너는 똑똑한 경제 선생님이야, 쉬운 문제 중심으로 금융 문제 1개를 5지 선다로 내어줘"
					+ " 항상 아래와 같은 형식을 맞추어서 대답해줘 "
					+ "문제. (문제)?\n"
					+ "1번 (선지).\n"
					+ "2번 (선지).\n"
					+ "3번 (선지).\n"
					+ "4번 (선지).\n"
					+ "5번 (선지).\n"
					+ "정답 (정답)\n")))
				.build();

			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(gptRequestDto);
			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(
				GPT_URL,
				HttpMethod.POST,
				entity,
				String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				log.info("GPT Response 200");
				GptResponseDto gptResponseDto = objectMapper.readValue(response.getBody(), GptResponseDto.class);
				String generatedQuestion = gptResponseDto.getContent();
				log.info("{}", generatedQuestion);

				// 생성된 질문과 답변을 QuizDto에 담아서 저장
				QuizDto quizDto = new QuizDto();
				quizDto.setQuizDetail(generatedQuestion);
				quizDto.setQuizAnswer("generatedQuestion"); // 여기에 답변을 설정해야 합니다.

				return new ResponseEntity<>(generatedQuestion, HttpStatus.OK);
			} else {
				log.info("Server Error");
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			log.info("exception = {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Optional<Quiz> showQuiz(Long quizId) {
		return quizRepository.findById(quizId);
	}

	public Optional<Quiz> findTodayQuiz() {
		List<Quiz> quizList = quizRepository.findAll();

		if (!quizList.isEmpty()) {
			Quiz todayQuiz = quizList.get(0);

			return Optional.of(todayQuiz);
		} else {
			return Optional.empty();
		}
	}

	public void deleteQuiz(Long quizId) {
		// ID를 사용하여 퀴즈 삭제
		Optional<Quiz> existingQuiz = quizRepository.findById(quizId);
		if (existingQuiz.isPresent()) {
			quizRepository.delete(existingQuiz.get());
		} else {
			// 해당 ID의 퀴즈가 없을 경우 처리
		}
	}
}

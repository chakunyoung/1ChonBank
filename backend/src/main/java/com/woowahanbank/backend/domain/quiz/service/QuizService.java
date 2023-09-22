package com.woowahanbank.backend.domain.quiz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanbank.backend.domain.quiz.domain.Quiz;
import com.woowahanbank.backend.domain.quiz.dto.GptMessage;
import com.woowahanbank.backend.domain.quiz.dto.GptRequestDto;
import com.woowahanbank.backend.domain.quiz.dto.GptResponseDto;
import com.woowahanbank.backend.domain.quiz.repository.QuizRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import com.woowahanbank.backend.global.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private static final String GPT_URL = "https://api.openai.com/v1/chat/completions";
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    @Value("${gpt.key}")
    private String gptKey;
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public void chatGpt(String key) {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(JwtTokenUtil.HEADER_STRING, JwtTokenUtil.TOKEN_PREFIX + key);
    }

    public ResponseEntity<String> createQuestionsBasedOnIntro(Map<String, String> body) {
        log.info("AI 질문 Generate!");
        try {

            GptRequestDto gptRequestDto = GptRequestDto.builder()
                    .model("gpt-3.5-turbo")
                    // .model("gpt-4")
                    .messages(List.of(new GptMessage("system", "너는 똑똑한 경제 선생님이야, 쉬운 문제 중심으로 금융 문제 1개를 5지 선다로 내어줘"
                            + " 항상 아래와 같은 형식을 맞추어서 대답해줘 그리고 각 형식은 한 줄로 만 답 해주고 한국말로 답해줘"
                            + "시작: (오늘 날짜)\n"
                            + "문제: (문제, 글자수는 30글자 이하로)\n"
                            + "1번 선지: (1번 선지, 선지는 무조건 숫자로만)\n"
                            + "2번 선지: (2번 선지, 선지는 무조건 숫자로만)\n"
                            + "3번 선지: (3번 선지, 선지는 무조건 숫자로만)\n"
                            + "4번 선지: (4번 선지, 선지는 무조건 숫자로만)\n"
                            + "5번 선지: (5번 선지, 선지는 무조건 숫자로만)n"
                            + "정답: (선지 번호만 출력해, 앞에 '정답: '를 꼭 넣어줘)\n"
                            + "해설: (해설, 앞에 '해설: '를 꼭 넣어줘)\n")))
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
                // GptResponseDto gptResponseDto = objectMapper.readValue(jsonResponse, GptResponseDto.class);
                System.out.println(gptResponseDto);
                String generatedQuestion = gptResponseDto.getChoices().get(0).getMessage().getContent();
                String choicesDetails = "";

                for (GptResponseDto.Choice choice : gptResponseDto.getChoices()) {
                    choicesDetails += "Choice { index=" + choice.getIndex()
                            + ", message { role=" + choice.getMessage().getRole()
                            + ", content=" + choice.getMessage().getContent();
                }

                // String logMessage = "GptResponseDto(id=" + gptResponseDto.getId()
                // 	+ ", object=" + gptResponseDto.getObject()
                // 	+ ", created=" + gptResponseDto.getCreated()
                // 	+ ", model=" + gptResponseDto.getModel()
                // 	+ ", choices=[" + choicesDetails + "])";
                //
                // log.info(logMessage);
                log.info("{}", generatedQuestion);

                String choice1 = "";
                String choice2 = "";
                String choice3 = "";
                String choice4 = "";
                String choice5 = "";
                String answer = "";
                String commentary = "";
                String question = "";

                String[] parts = choicesDetails.split("\n");
//				for (String part : parts) {
//					 if (part.startsWith("문제")){
//						question = part.substring("문제:".length()).trim();
//						System.out.println(question);
//					} else if (part.startsWith("1번 선지")) {
//						choice1 = part.substring("1번 선지:".length()).trim();
//						System.out.println(choice1);
//					} else if (part.startsWith("2번 선지")) {
//						choice2 = part.substring("2번 선지:".length()).trim();
//						System.out.println(choice2);
//					} else if (part.startsWith("3번 선지")) {
//						choice3 = part.substring("3번 선지:".length()).trim();
//						System.out.println(choice3);
//					} else if (part.startsWith("4번 선지")) {
//						choice4 = part.substring("4번 선지:".length()).trim();
//						System.out.println(choice4);
//					} else if (part.startsWith("5번 선지")) {
//						choice5 = part.substring("5번 선지:".length()).trim();
//						System.out.println(choice5);
//					} else if (part.startsWith("정답")) {
//						answer = part.substring("정답:".length()).trim();
//						System.out.println(answer);
//					} else if (part.startsWith("해설")) {
//						commentary = part.substring("해설:".length()).trim();
//						System.out.println(commentary);
//					}
//				}


                // 문자열에서 데이터 추출을 위한 정규 표현식 패턴
                Pattern pattern = Pattern.compile("(문제|1번 선지|2번 선지|3번 선지|4번 선지|5번 선지|정답|해설):\\s*(.*)");

                for (String part : parts) {

                    Matcher matcher = pattern.matcher(part);
                    if (matcher.matches()) {
                        String key = matcher.group(1); // 매치된 패턴의 첫 번째 그룹 (문제, 1번 선지, ...)
                        String value = matcher.group(2); // 매치된 패턴의 두 번째 그룹 (실제 데이터)

                        // key에 따라 데이터 저장 또는 출력
                        if ("문제".equals(key)) {
                            question = value;
                            System.out.println(question);
                        } else if ("1번 선지".equals(key)) {
                            choice1 = value;
                            System.out.println(choice1);
                        } else if ("2번 선지".equals(key)) {
                            choice2 = value;
                            System.out.println(choice2);
                        } else if ("3번 선지".equals(key)) {
                            choice3 = value;
                            System.out.println(choice3);
                        } else if ("4번 선지".equals(key)) {
                            choice4 = value;
                            System.out.println(choice4);
                        } else if ("5번 선지".equals(key)) {
                            choice5 = value;
                            System.out.println(choice5);
                        } else if ("정답".equals(key)) {
                            answer = value;
                            System.out.println(answer);
                        } else if ("해설".equals(key)) {
                            commentary = value;
                            System.out.println(commentary);
                        }

                    }
                }


                Quiz build = Quiz.builder()
                        .quizQuestion(question)
                        .quizChoice1(choice1)
                        .quizChoice2(choice2)
                        .quizChoice3(choice3)
                        .quizChoice4(choice4)
                        .quizChoice5(choice5)
                        .quizAnswer(answer)
                        .quizCommentary(commentary)
                        .build();

                quizRepository.save(build);

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

    @Scheduled(cron = "0 0 12 * * ?") // 매일 정오 12시에 실행
    public void scheduleCreateQuestionsBasedOnIntro() {
        deleteQuiz();
        chatGpt(gptKey);
        createQuestionsBasedOnIntro(null);
    }

    public Optional<Quiz> showQuiz(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public Optional<Quiz> findTodayQuiz() {
        List<Quiz> quizList = quizRepository.findAll();

        if (!quizList.isEmpty()) {
            Quiz todayQuiz = quizList.get(quizList.size()-1);

            return Optional.of(todayQuiz);
        } else {
            return Optional.empty();
        }
    }

    public void makeQuiz() {
        chatGpt(gptKey);
        createQuestionsBasedOnIntro(null);

    }

    public void deleteQuiz() {
        quizRepository.deleteAll();
    }

    public void solvedQuiz(User user) {
        user.solvedQuiz(1L);
        userRepository.save(user);
    }
}

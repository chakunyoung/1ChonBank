import React, { useState, useEffect } from "react";
import axios from "axios";

import apis from "services/api/apis";
import "./Quiz.css";
import { useDispatch, useSelector } from "react-redux";
import { setUser } from "redux/Auth";
import { setQuizInfo } from "redux/QuizInfo";
import { useNavigate } from "react-router-dom";
import Footer from "components/common/Footer";
import Wrapper from "components/common/Wrapper";
import { motion } from "framer-motion";

const Quiz = () => {
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [error, setError] = useState(null);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const user = useSelector((state) => state.auth.user);

  const quizInfo = useSelector((state) => state.quizInfo.quizInfo);

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const response = await apis.get("/api/quiz/todayQuiz");
        const quizData = response.data;
        dispatch(setQuizInfo(quizData));
        // console.log(quizData);
        setError(null);
      } catch (error) {
        console.error("퀴즈 데이터를 가져오는 데 실패했습니다.", error);
        setError("퀴즈 데이터를 가져오는 데 실패했습니다.");
      }
    };

    fetchQuiz();
  }, []);

  const handleAnswerSelect = (answer) => {
    setSelectedAnswer(answer);
  };

  const handleSubmit = async () => {
    const updatedUser = { ...user, quiz: 1 };
    dispatch(setUser(updatedUser));
    try {
      const response = await apis.post("/api/quiz/solved");
      if (response.status === 200) {
        // 요청이 성공했을 때의 동작
      } else {
        // 요청이 실패했을 때의 동작
        // console.log("터짐");
      }
    } catch (error) {
      // 요청 중에 예외가 발생했을 때의 동작
    }

    if (selectedAnswer !== null) {
      if (selectedAnswer + 1 == quizInfo.quizAnswer) {
        alert("정답");
        navigate("/QuizResult", {
          state: {
            ans: 1,
          },
        });
      } else {
        alert("오답");
        navigate("/QuizResult", {
          state: {
            ans: 0,
          },
        });
      }
    } else {
      alert("답변을 선택해주세요!");
    }
  };

  return (
    <motion.div
      initial={Wrapper.initial}
      animate={Wrapper.animate}
      exit={Wrapper.exit}>
      <div className="QuizContainer">
        <h1>오늘의 퀴즈</h1>
        <div>
          {error ? (
            <p>{error}</p>
          ) : quizInfo ? (
            <div>
              <div className="quizarea">
                <p>{quizInfo.quizQuestion}</p>
              </div>
              <div className="choiceContainer">
                {Object.values(quizInfo)
                  .slice(1, 6)
                  .map((choice, index) => (
                    <div
                      className={`choicearea ${
                        selectedAnswer === index ? "selectedBox" : ""
                      }`}
                      onClick={() => handleAnswerSelect(index)}
                      key={index}>
                      {choice}
                    </div>
                  ))}
              </div>
              <button onClick={handleSubmit} className="quiz-submit">
                정답 확인
              </button>
            </div>
          ) : (
            <p>퀴즈 데이터를 불러오는 중...</p>
          )}
        </div>
        <div className="quiz-container">
          <Footer />
        </div>
      </div>
    </motion.div>
  );
};

export default Quiz;

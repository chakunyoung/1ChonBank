import React, { useState, useEffect } from "react";
import apis from "services/api/apis";
import "./QuizResult.css";

import { useNavigate, useLocation } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import Footer from "components/common/Footer";
import { setQuizInfo } from "redux/QuizInfo";

const QuizResult = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const answer = location.state ? location.state.ans : null;
  const quizInfo = useSelector((state) => state.quizInfo.quizInfo);
  const dispatch = useDispatch();

  const handleSubmit = () => {
    navigate("/");
  };

  useEffect(() => {
    async function fetchData() {
      if (answer === 1) {
        try {
          // console.log("트라이시작함");
          const updatedPoint = 500;
          const response = await apis.put("/api/quiz/updatePoint", {
            quizPoint: updatedPoint,
          });
          // console.log(response.data);
        } catch (error) {
          console.error("점수 입력을 실패했습니다.", error);
        }
      }
    }
    fetchData();
  }, []);

  return (
    <div className="QuizContainer">
      {answer === 1 ? <p>정답입니다!</p> : <p>오답입니다.</p>}
      <div>포인트 획득</div>
      {answer === 1 ? <p>+500P</p> : <p>+0</p>}

      <div className="answerarea">정답: {quizInfo.quizAnswer}번</div>
      <div className="commentaryarea">
        해설: <br />
        {quizInfo.quizCommentary}
      </div>

      <button onClick={handleSubmit} className="okay-button">
        홈으로
      </button>
      <div className="quizresultfooter">
        <Footer />
      </div>
    </div>
  );
};

export default QuizResult;

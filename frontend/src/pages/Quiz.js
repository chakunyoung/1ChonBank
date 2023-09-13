import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Quiz.css';

const Quiz = () => {
  const [quiz, setQuiz] = useState(null); 
  const [selectedAnswer, setSelectedAnswer] = useState(null); 
  const [error, setError] = useState(null); 

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const response = await axios.get('/api/quiz/todayQuiz'); 
        const quizData = response.data; 

        setQuiz(quizData);
        setError(null); 
      } catch (error) {
        console.error('퀴즈 데이터를 가져오는 데 실패했습니다.', error);
        setError('퀴즈 데이터를 가져오는 데 실패했습니다.');
      }
    };


    fetchQuiz();
  }, []); 

  const handleAnswerSelect = (answer) => {
    setSelectedAnswer(answer);
  };

  // 퀴즈 제출 핸들러
  const handleSubmit = () => {
    if (selectedAnswer !== null) {
      // 선택한 답변을 서버로 보내거나 다른 처리를 수행할 수 있습니다.
      console.log('선택한 답변:', selectedAnswer);
    } else {
      // 사용자가 답변을 선택하지 않은 경우에 대한 처리
      console.log('답변을 선택하세요.');
    }
  };

  return (
    <div className='QuizContainer'>
      <h1>오늘의 퀴즈</h1>
      <div>
        {error ? (
          <p>{error}</p>
        ) : quiz ? (
          <div>
            <div className='quizarea'>
              <p>{quiz.quizQuestion}</p>
            </div>
            <div className='choiceContainer'>
              {Object.values(quiz).slice(1, 6).map((choice, index) => (
                <div className={`choicearea ${selectedAnswer === choice ? 'selectedBox' : ''}`} onClick={() => handleAnswerSelect(choice)} key={index}>
                  {choice}
                  <div className={`circle ${selectedAnswer === choice ? 'selected' : ''}`}></div>
                </div>
              ))}
                <button onClick={handleSubmit} className="custom-button">
                  정답 제출
                </button>
            </div>
          </div>
        ) : (
          <p>퀴즈 데이터를 불러오는 중...</p>
        )}
      </div>
    </div>
  );
};

export default Quiz;

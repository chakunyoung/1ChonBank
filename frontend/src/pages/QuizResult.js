import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './QuizResult.css';

import { useNavigate, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Footer from 'components/common/Footer';

const QuizResult = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const answer = location.state ? location.state.ans : null;
  const quizInfo = useSelector((state) => state.quizInfo.quizInfo);
  
  const handleSubmit = () => {
    navigate("/");
  }


  return (
    <div className='QuizContainer'>


      {answer === 1 ? <p>정답입니다!</p> : <p>오답입니다.</p>}
      <div>포인트 획득</div>
      {answer === 1 ? <p>+500P</p> : <p>+0</p>}

  
       

      <div className='answerarea'>
        정답 : {quizInfo.quizAnswer}번
      </div>
      <div className='commentaryarea'>
        해설 : <br/>
        {quizInfo.quizCommentary}     
      </div>

      <button onClick={handleSubmit} className="okay-button">
        홈으로
      </button>
      <div className='quizresultfooter'>
      <Footer/>
      </div>
    </div>
  );
};


export default QuizResult;

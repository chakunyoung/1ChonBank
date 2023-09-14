import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from 'pages/Home';
import Register from 'pages/Register';
import Mypage from 'pages/Mypage';
import Mission from 'pages/Mission';
import KakaoLoginRedirect from 'pages/user/KakaoLoginRedirect';
import SelectFinance from 'pages/Finance/SelectFinance';
import MakeFinance from 'pages/Finance/MakeFinance';
import FinanceList from 'pages/Finance/FinanceList';
<<<<<<< HEAD
import DailyQuiz from 'pages/Quiz';
=======
import Quiz from 'pages/Quiz';
import QuizResult from 'pages/QuizResult';
>>>>>>> 2e76c56 (feat: front-mypage-gpt)

const RoutePath = () => {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/mission" element={<Mission />} />
        <Route path="/selectFinance" element={<SelectFinance />} />
        <Route path="/makeFinance" element={<MakeFinance />} />
<<<<<<< HEAD
        <Route path="/financelist" element={<FinanceList />} />
        <Route path="/quiz" element={<DailyQuiz />} />
=======
        <Route path="/quiz" element={<Quiz />} />
        <Route path="/quizResult" element={<QuizResult />} />
        <Route path="/financelist" element={<FinanceList />} />
>>>>>>> 2e76c56 (feat: front-mypage-gpt)
        <Route
          path="/login/kakao/code"
          element={<KakaoLoginRedirect />}
        />
      </Routes>
  );
};

export default RoutePath;
        

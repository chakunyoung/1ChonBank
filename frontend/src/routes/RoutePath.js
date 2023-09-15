import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from 'pages/Home';
import Register from 'pages/Register';
import Mypage from 'pages/Mypage';
import Mission from 'pages/Mission';
import KakaoLoginRedirect from 'pages/user/KakaoLoginRedirect';
import DailyQuiz from 'pages/Quiz';
import Quiz from 'pages/Quiz';
import SelectFinance from 'pages/finance/SelectFinance';
import MakeFinance from 'pages/finance/MakeFinance';
import FinanceList from 'pages/finance/FinanceList';
import FinanceDetail from 'pages/finance/FinanceDetail';
import QuizResult from 'pages/QuizResult';
import Account from 'pages/account/Account';
import AccountDetail from 'pages/account/AccountDetail';


const RoutePath = () => {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/mission" element={<Mission />} />
        <Route path="/selectFinance" element={<SelectFinance />} />
        <Route path="/makeFinance" element={<MakeFinance />} />
        <Route path="/financial" element={<FinanceList />} />
        <Route path="/financeDetail" element={<FinanceDetail />} />
        <Route path="/financelist" element={<FinanceList />} />
        <Route path="/quiz" element={<DailyQuiz />} />
        <Route path="/quiz" element={<Quiz />} />
        <Route path="/quizResult" element={<QuizResult />} />
        <Route path="/financelist" element={<FinanceList />} />
        <Route path="/account" element={<Account />} />
        <Route path="/accountDetail" element={<AccountDetail />} />
        <Route
          path="/login/kakao/code"
          element={<KakaoLoginRedirect />}
        />
      </Routes>
  );
};

export default RoutePath;
        

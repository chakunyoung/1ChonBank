import React from "react";
import { Route, Routes } from "react-router-dom";
import TokenVerification from "components/auth/TokenVerificationToRoot";
import Home from "pages/Home";
import Register from "pages/Register";
import Mypage from "pages/Mypage";
import Mission from "pages/mission/MissionMake";
import KakaoLoginRedirect from "pages/user/KakaoLoginRedirect";
import GoogleLoginRedirect from "pages/user/GoogleLoginRedirect";
import Quiz from "pages/Quiz";
import SelectFinance from "pages/finance/SelectFinance";
import MakeFinance from "pages/finance/MakeFinance";
import FinanceList from "pages/finance/FinanceList";
import FinanceDetail from "pages/finance/FinanceDetail";
import QuizResult from "pages/QuizResult";
import Account from "pages/account/Account";
import AccountDetail from "pages/account/AccountDetail";

import CreateFamily from "pages/family/CreateFamily";
import MyFamily from "pages/family/MyFamily";
import MissionList from "pages/mission/MissionList";
import MissionMake from "pages/mission/MissionMake";

const RoutePath = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/mypage"
        element={
          // <TokenVerification>
          <Mypage />
          // </TokenVerification>
        }
      />
      <Route path="/mission" element={<MissionMake />} />
      <Route path="/selectFinance" element={<SelectFinance />} />
      <Route path="/makeFinance" element={<MakeFinance />} />
      <Route path="/financial" element={<FinanceList />} />
      <Route path="/financeDetail" element={<FinanceDetail />} />
      <Route path="/financelist" element={<FinanceList />} />

      <Route path="/quiz" element={<Quiz />} />
      <Route path="/quizResult" element={<QuizResult />} />
      <Route path="/financelist" element={<FinanceList />} />
      <Route path="/account" element={<Account />} />
      <Route path="/accountDetail" element={<AccountDetail />} />
      <Route path="/createFamily" element={<CreateFamily />} />
      <Route path="/myFamily" element={<MyFamily />} />

      <Route path="/login/kakao/code" element={<KakaoLoginRedirect />} />
      <Route
        path="/login/oauth2/code/google"
        element={<GoogleLoginRedirect />}
      />
      <Route path="/missionList" element={<MissionList />} />
    </Routes>
  );
};

export default RoutePath;

import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from 'pages/Home';
import Register from 'pages/Register';
import Mypage from 'pages/Mypage';
import Mission from 'pages/Mission';
import Login from 'pages/user/Login';

import KakaoLoginRedirect from 'pages/user/KakaoLoginRedirect';
import Signup from 'pages/user/Signup';

const RoutePath = () => {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/mission" element={<Mission />} />
        <Route path="/signup" element={<Signup />} />
        <Route
          path="/login/kakao/code"
          element={<KakaoLoginRedirect />}
        />
      </Routes>
  );
};

export default RoutePath;
        
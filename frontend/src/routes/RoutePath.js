import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from 'pages/Home';
import Login from 'pages/user/Login';
import KakaoLoginRedirect from 'pages/user/KakaoLoginRedirect';

const RoutePath = () => {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/login/kakao/code"
          element={<KakaoLoginRedirect />}
        />
      </Routes>
  );
};

export default RoutePath;
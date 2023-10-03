import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const TokenVerificationToMyPage = ({ children }) => {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("access-token");

  useEffect(() => {
    const verifyToken = async () => {
      if (accessToken) {
        try {
          await axios.post("/api/users/verify-token", null, {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${accessToken}`,
            },
          });
          console.log("토큰이 유효합니다. 접근할 수 없습니다.");
          navigate("/mypage");
        } catch (error) {
          console.log("토큰이 유효하지 않습니다. 로그인으로 이동합니다.");
        }
      }
    };

    verifyToken();
  }, [accessToken, navigate]);

  return <>{children}</>;
};

export default TokenVerificationToMyPage;

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const TokenVerificationToRoot = ({ children }) => {
  const [isVerified, setIsVerified] = useState(null);
  const navigate = useNavigate();

  const accessToken = localStorage.getItem("access-token");

  useEffect(() => {
    const verifyToken = async () => {
      if (!accessToken) {
        // console.log("토큰이 없습니다. 루트로 리다이렉트합니다.");
        navigate("/");
        return;
      }

      try {
        const response = await axios.post("/api/users/verify-token", null, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setIsVerified(true);
      } catch (error) {
        // console.log("서버에 유저 정보가 없거나, 토큰이 유효하지 않습니다.");
        setIsVerified(false);
        navigate("/");
      }
    };

    if (isVerified === null) {
      verifyToken();
    }
  }, [accessToken, navigate, isVerified]);

  return isVerified === null ? <div>Loading...</div> : <>{children}</>;
};

export default TokenVerificationToRoot;

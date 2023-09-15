import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const TokenVerification = ({ children }) => {
  const [isVerified, setIsVerified] = useState(null);
  const [redirected, setRedirected] = useState(false);
  const navigate = useNavigate();

  const persistData = JSON.parse(localStorage.getItem("persist:root"));
  let auth, accessToken;

  if (persistData && persistData.auth) {
    auth = JSON.parse(persistData.auth);
    accessToken = auth.accessToken;
  }

  useEffect(() => {
    if ((!persistData || !auth || !accessToken) && !redirected) {
      setRedirected(true);
      navigate('/');
      return;
    }

    const verifyToken = async () => {
      try {
        const response = await axios.post('/api/users/verify-token', null, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken}`,
          },
        });
        setIsVerified(response.data.isValid);
      } catch (error) {
        console.log("서버에 유저 정보가 없거나, 토큰이 유효하지 않습니다.");
        setIsVerified(false);
        if (!redirected) {
          setRedirected(true);
          navigate('/');
        }
      }
    };

    if (!redirected) {
      verifyToken();
    }
  }, [accessToken, auth, navigate, persistData, redirected]);

  return isVerified === null ? <div>Loading...</div> : <>{children}</>;
};

export default TokenVerification;

import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { setAccessToken, setUser, setFirebaseToken, setRefreshToken } from "redux/Auth"; // 필요한 액션들을 import 합니다.
import apis from "services/api/apis";
import {
  getFirebaseToken,
  sendWebPushInfomation,
} from "services/api/FirebaseAPI";

function GoogleLoginRedirect() {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const nickname = useSelector((state) => state.auth.nickname);
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.auth.user);

  useEffect(() => {
    const urlParams = new URLSearchParams(location.search);
    const code = urlParams.get("code");

    if (code) {
      handleGoogleLogin(code);
    }
  }, [location]);

  const handleGoogleLogin = async (code) => {
    try {
      const response = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/api/auth/google`,
        { code }
      );
      console.log("유저가 인증되었습니다.", response.data);

      const accessToken = response.data.data["access-token"];
      const payloadBase64 = accessToken.split(".")[1];
      const decodedPayload = atob(payloadBase64);
      const payloadObj = JSON.parse(decodedPayload);
      const userId = payloadObj.sub;

      dispatch(setAccessToken(accessToken));
      dispatch(setRefreshToken(response.data.data["refresh-token"]));
      if (user === null) {
        const tempUser = {
          userId: userId,
        };
        dispatch(setUser(tempUser));
      }

      fetchUserData(userId, navigate, dispatch, nickname, token);
    } catch (error) {
      console.error("구글 로그인 에러:", error);
      navigate("/");
      alert("구글 로그인 오류.");
    }
  };

  const fetchUserData = async (userId, navigate, dispatch, nickname, token) => {
    try {
      const response = await apis.get(`/api/user/${userId}`);
      const userData = response.data.data;

      dispatch(setUser(userData));

      if (userData.roles === null) {
        navigate("/register");
      } else {
        navigate("/mypage");
      }

      const firebaseToken = await getFirebaseToken();
      if (firebaseToken) {
        dispatch(setFirebaseToken(firebaseToken));
        console.log("FIREBASE - token updated successfully");

        if (userData.nickname && firebaseToken) {
          await sendWebPushInfomation(userData.nickname, firebaseToken)
            .then((res) => {
              console.log(
                "FIREBASE - send backend firebase token successfully"
              );
            })
            .catch((error) => {
              console.error("Error sending web push notification:", error);
            });
        }
      }
    } catch (error) {
      console.error("API 오류:", error);
    }
  };

  return <div>Redirecting...</div>;
}

export default GoogleLoginRedirect;

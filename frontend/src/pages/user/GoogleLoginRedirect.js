import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { setUser, setFirebaseToken } from "redux/Auth"; // 필요한 액션들을 import 합니다.
import {
  getFirebaseToken,
  sendWebPushInfomation,
} from "services/api/FirebaseAPI";
import { setFamilyName } from "redux/Family";

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

      const accessToken = response.data.data["access-token"];
      const payloadBase64 = accessToken.split(".")[1];
      const decodedPayload = atob(payloadBase64);
      const payloadObj = JSON.parse(decodedPayload);
      const userId = payloadObj.sub;

      localStorage.setItem("access-token", accessToken);
      localStorage.setItem(
        "refresh-token",
        response.data.data["refresh-token"]
      );
      if (user === null) {
        dispatch(setUser({ ...user, userId: userId }));
      }

      let userData = await fetchUserData(
        userId,
        navigate,
        dispatch,
        nickname,
        token
      );
      //await performFirebaseTokenTask(userData, dispatch);
    } catch (error) {
      console.error("구글 로그인 에러:", error);
      navigate("/");
      alert("구글 로그인 오류.");
    }
  };

  const fetchUserData = async (userId, navigate, dispatch, nickname, token) => {
    try {
      const response = await axios.get(`/api/user/${userId}`);
      const userData = response.data.data;
      dispatch(setUser(userData));
      dispatch(setFamilyName(userData.familyName));
      if (userData.roles === null) {
        navigate("/register");
      } else {
        navigate("/mypage");
      }

      return userData;
    } catch (error) {
      console.error("API 오류:", error);
    }
  };

  async function performFirebaseTokenTask(userData, dispatch) {
    const firebaseToken = await getFirebaseToken();
    if (firebaseToken) {
      dispatch(setFirebaseToken(firebaseToken));
      console.log("FIREBASE - token updated successfully");

      if (userData.nickname && firebaseToken) {
        await sendWebPushInfomation(userData.nickname, firebaseToken)
          .then((res) => {
            console.log("FIREBASE - send backend firebase token successfully");
          })
          .catch((error) => {
            console.error("Error sending web push notification:", error);
          });
      }
    }
  }

  return <div>Redirecting...</div>;
}

export default GoogleLoginRedirect;

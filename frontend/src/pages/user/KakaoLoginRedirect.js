import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import { kakaoLogin, setUser, setFirebaseToken } from "redux/Auth";
import { setFamilyName } from "redux/Family";

import axios from "axios";

const KakaoLoginRedirect = () => {
  const { search } = useLocation();

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);

  useEffect(() => {
    const bodyData = {
      grant_type: "authorization_code",
      client_id: process.env.REACT_APP_KAKAO_REST_API_KEY,
      redirect_uri: process.env.REACT_APP_REDIRECT_URI,
      client_secret: process.env.REACT_APP_KAKAO_SECRET_KEY,
      code: search.substring(6),
    };

    const queryStringBody = Object.keys(bodyData)
      .map((k) => encodeURIComponent(k) + "=" + encodeURI(bodyData[k]))
      .join("&");
    fetch("https://kauth.kakao.com/oauth/token", {
      method: "POST",
      headers: {
        "content-type": "application/x-www-form-urlencoded;charset=utf-8",
      },
      body: queryStringBody,
    })
      .then((res) => res.json())
      .then((data) => {
        dispatch(kakaoLogin(data["id_token"]))
          .unwrap()
          .then((data) => {
            const accessToken = data["access-token"];

            const payloadBase64 = accessToken.split(".")[1];
            const decodedPayload = atob(payloadBase64);
            const payloadObj = JSON.parse(decodedPayload);
            const userId = payloadObj.sub;

            localStorage.setItem("access-token", data["access-token"]);
            localStorage.setItem("refresh-token", data["refresh-token"]);

            return axios.get(`/api/user/${userId}`); // DB 에 저장된 유저 정보 가져오기
          })
          .then(async (response) => {
            const userData = response.data.data;
            dispatch(setUser(userData));
            dispatch(setFamilyName(userData.familyName));

            if (userData.roles === null) {
              navigate("/register");
            } else {
              navigate("/mypage");
            }
            return userData;
          })
          .catch((error) => {
            console.error("API 오류:", error);
            navigate("/");
            alert("카카오 로그인 오류.");
          });
      });

    return () => {};
  }, []);
  return <>카카오 리다이렉트 페이지</>;
};

export default KakaoLoginRedirect;

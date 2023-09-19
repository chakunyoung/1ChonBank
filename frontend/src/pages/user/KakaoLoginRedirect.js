import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

import { kakaoLogin, setUser, setFirebaseToken, setAccessToken } from "redux/Auth";
import { setFamilyName } from "redux/Family";

import apis from "services/api/apis";
import { getFirebaseToken, sendWebPushNotification } from "services/api/FirebaseAPI";

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
          .then(({ data }) => {
            console.log(data["access-token"]);
            dispatch(setAccessToken(data["access-token"]))
            const accessToken = data["access-token"]; // access-token을 가져옵니다.

            // JWT의 payload 부분을 디코딩합니다.
            const payloadBase64 = accessToken.split('.')[1]; // JWT의 payload 부분은 두 번째 부분입니다.
            const decodedPayload = atob(payloadBase64); // Base64 디코딩
            // JSON 형식으로 파싱하여 payload 객체를 가져옵니다.
            const payloadObj = JSON.parse(decodedPayload);
            const userId = payloadObj.sub;
            // dispatch(setUserId(userId));

            return apis.get(`/api/user/${userId}`);
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

            const firebaseToken = await getFirebaseToken();
            if (firebaseToken) {
              dispatch(setFirebaseToken(firebaseToken));
              console.log('FIREBASE - token updated successfully');
            }

            return { userData: userData, token: firebaseToken };
          })
          .then(async (data) => {
            console.log(data);
            if (data.userData.nickname && data.token) {
              await sendWebPushNotification(data.userData.nickname, data.token);
              console.log('FIREBASE - send backend firebase token successfully');
            }
            navigate("/");
          })
          .catch((error) => {
            console.error("API 오류:", error);
            navigate("/login");
            alert("카카오 로그인 오류.");
          });
      });

    return () => { };
  }, []);
  return <>카카오 리다이렉트 페이지</>;
};

export default KakaoLoginRedirect;

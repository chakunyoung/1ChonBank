import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { kakaoLogin, setUserId, setNickname, setServerNickname, setIsLogin, setRoles } from "redux/Auth";
import { setAccessToken } from "redux/Auth";
import apis from "services/api/apis";

const KakaoLoginRedirect = () => {
  const { search } = useLocation();

  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    console.log("trigger");

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
      .then((res) => {
        console.log(bodyData);
        console.log(res);
        return res.json()
      })
      .then((data) => {
        console.log(data);
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
            // payload에서 sub 값을 추출합니다.
            const userId = payloadObj.sub;

            apis.get(`/user/${userId}`)
              .then((response) => {
                // 성공적인 API 응답을 처리합니다.
                const userData = response.data.data;
                dispatch(setUserId(userData.userId));
                dispatch(setNickname(userData.nickname));
                dispatch(setServerNickname(userData.serverNickname));
                dispatch(setRoles(userData.roles));
                dispatch(setIsLogin(true));
                console.log(userData);
                // type 값에 따라 navigate 처리
                if (userData.roles === null) {
                  // type이 null인 경우 /sign/up으로 navigate
                  navigate('/signup');
                } else {
                  // type이 null이 아닌 경우 /로 navigate
                  navigate('/');
                }
              })
              .catch((error) => {
                // API 오류를 처리합니다.
                console.error('API 오류:', error);
              });



            navigate("/");
          })
          .catch((error) => {
            navigate("/login");
            alert("카카오 로그인 오류."); //todo 이쁜 거로 바꾸기 sweetalert (?)
          });
      });

    return () => { };
  }, []);

  return (
    <>
      카카오 리다이렉트 페이지
    </>
  );
};

export default KakaoLoginRedirect;
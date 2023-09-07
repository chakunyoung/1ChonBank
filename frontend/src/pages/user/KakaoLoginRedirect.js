import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { kakaoLogin, getUserInfo } from "redux/Auth";

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
      client_secret : process.env.REACT_APP_KAKAO_SECRET_KEY,
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
            console.log(data["id_token"]);
            console.log(data["access-token"]);
            dispatch(getUserInfo(data["access-token"]));
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
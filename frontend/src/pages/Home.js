import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Home.css";
import char1 from "assets/char1x4.png";
import Kakaoicon from "assets/kakao.svg";
import Googleicon from "assets/btn_google_signin_light_focus_web@2x.png";

const Home = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const data = JSON.parse(localStorage.getItem("persist:root"));
    if (data) {
      const authData = JSON.parse(data.auth);

      if (authData.user && authData.accessToken) {
        console.log('Access Token:', authData.accessToken);
        console.log('user nickname:', authData.user.nickname);
        navigate('/mypage');
      }
    }
  }, [navigate]);

  const handleKakaoLoginClick = () => {
    window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code`;
  };

  const handleGoogleLoginClick = () => {
    window.location.href = `${process.env.REACT_APP_API_BASE_URL}/oauth2/authorization/google`;
  };

  return (
    <div className="Homecontainer">
      <div className="char1-container">
        <div className="char1-text">
          <p>일촌페이</p>
        </div>
        <img src={char1} alt="char1" style={{ width: "200px" }} />
      </div>
      <div className="Buttoncontainer">
        <button
          onClick={handleKakaoLoginClick}
          className="kakao-login-button"
          style={{ margin: "5px" }}>
          <img src={Kakaoicon} />
          &nbsp; 카카오 로그인
        </button>

        <button
          onClick={handleGoogleLoginClick}
          // className="kakao-login-button"
          style={{ margin: "5px" }}>
          <img src={Googleicon} />
          &nbsp; 구글 로그인
        </button>
        <div></div>
      </div>
    </div>
  );
};

export default Home;

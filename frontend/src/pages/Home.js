import React from "react";
import "./Home.css";
import char1 from "assets/char1x4.png";
import Kakaoicon from "assets/kakao.svg";
import Googleicon from "assets/google.svg";
import Bubble from "assets/bubble.png";
import Logo from "assets/1chonbank.png";

const NUM_BUBBLES = 15; // 원하는 Bubble 개수

const Home = () => {

  const handleKakaoLoginClick = () => {
    window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code`;
  };

  const handleGoogleLoginClick = () => {
    window.location.href = `${process.env.REACT_APP_API_BASE_URL}/oauth2/authorization/google`;
  };
  const renderBubbles = () => {
    const bubbles = [];
    for (let i = 0; i < NUM_BUBBLES; i++) {
      const left = `${Math.random() * 83}%`; // 상대적인 위치 (0%에서 100% 사이에서 랜덤)
      const top = `${Math.random() * 90}%`; // 상대적인 위치 (0%에서 100% 사이에서 랜덤)
      const size = `${Math.random() * 50 + 20}px`;

      const bubbleStyle = {
        position: "absolute",
        left: left,
        top: top,
        width: size,
        height: size,
      };
      bubbleStyle["--delay"] = Math.random();

      bubbles.push(
        <img
          key={i}
          className="bubble"
          style={bubbleStyle}
          src={Bubble}
          alt={`Bubble ${i + 1}`}
        />
      );
    }
    return bubbles;
  };

  return (
    <div className="Homecontainer"> 
        <div className="char1-text">
          <img src={Logo} className="homelogo"/>
        </div>
      <div className="Buttoncontainer">

        <button
          onClick={handleKakaoLoginClick}
          className="kakao-login-button"
          style={{ margin: "5px" }}>
          <img src={Kakaoicon} className="kakao-icon" />
          &nbsp; 카카오 로그인
        </button>

        <button
          onClick={handleGoogleLoginClick}
          className="google-login-button"
          style={{ margin: "5px" }}>
          <img src={Googleicon} className="google-icon" />
          &nbsp; 구글 로그인
        </button>
        <div></div>
      </div>
      {renderBubbles()} {/* 여러 개의 Bubble 이미지 렌더링 */}

    </div>
  );
};

export default Home;

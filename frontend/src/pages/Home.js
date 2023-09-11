import React from 'react';
import { Link } from 'react-router-dom'; // Link 컴포넌트 import
import './Home.css';
import char1 from 'assets/char1x4.png';
import Kakaoicon from 'assets/kakao.svg';
import Signup from "./user/Signup";

const Home = () => {
  const handleKakaoLoginClick = () => {
    // 여기에 카카오 로그인을 처리하는 로직을 추가하세요.
    // 버튼을 클릭할 때 실행될 함수입니다.
  };

  return (
    <div className='Homecontainer'>
      <div className='char1-container'>
        <div className='char1-text'>
          <p>일촌페이</p>
        </div>
        <img src={char1} alt="char1" style={{ width: '200px' }} />
      </div>
      <div className='Buttoncontainer'>
        <button onClick={handleKakaoLoginClick} className="kakao-login-button" style={{margin: '5px' }}>
        <img src={Kakaoicon}/>&nbsp;
        카카오 로그인
        </button>
        <div>
        </div>
        <div className='register-buttoncontainer'>
        <Link to="/register"> {/* Register 페이지로 이동 */}
            <button className="register-button" style={{ margin:'5px' }}>
              회원가입
            </button>
          </Link>
          <div className="background-box"></div>

      </div>
      </div>
    </div>
  );
};

export default Home;

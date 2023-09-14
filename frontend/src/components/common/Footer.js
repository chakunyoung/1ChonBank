import React, { useState } from 'react';
import './Footer.css';
// import { useHistory } from 'react-router-dom';
import { IoArrowBackOutline,IoHome, IoPeopleSharp, IoEllipsisHorizontal, IoCardOutline } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';



function Footer() {
    const navigate = useNavigate();

    const handleGoBack = () => {
        window.history.back(); // 브라우저 뒤로가기 기능 호출
      
    // 뒤로가기 버튼 클릭 시 실행할 함수
    // const handleGoBack = () => {
    //     const history = useHistory();
    //     history.goBack(); // React Router를 사용한 뒤로가기
      
      // 여기에 뒤로가기 동작을 구현하거나,
      // 브라우저에서 사용하는 뒤로가기 기능을 호출할 수 있습니다.
    };

    const handleMyPage = () => {
      navigate("/");
    }
  
      
  return (
    <div className="footer-container">
      <button className='footer-left footer-button'>
        <IoArrowBackOutline className='icon' onClick={handleGoBack} />
      </button>
      <button className='footer-menu footer-button'>
      <IoCardOutline className='icon' style={{ background: 'linear-gradient(93.46deg, #133FDB 0%, rgba(183, 0, 77, 0.3) 103.75%)', WebkitBackgroundClip: 'text', backgroundClip: 'text', WebkitTextFillColor: 'transparent' }} />
      </button>

      <button className='footer-home footer-button'>
        <div className='icon-container'>
          <div className='gradient-circle'>
          <IoHome className='icon' onClick={handleMyPage}/>
          </div>
        </div>
      </button>

      <button className='footer-account footer-button'>
        <IoPeopleSharp className='icon' />
      </button>
      <button className='footer-etc footer-button'>
        <IoEllipsisHorizontal className='icon' />
      </button>
    </div>
  );
}

export default Footer;
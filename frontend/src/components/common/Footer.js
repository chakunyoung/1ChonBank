import React, { useState } from 'react';
import './Footer.css';
import { IoArrowBackOutline, IoHome, IoPeopleSharp, IoEllipsisHorizontal, IoCardOutline } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';

function Footer() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-1);
  };

  const handleMyPage = () => {
    navigate("/myPage");
  }

  const handleAccount = () => {
    navigate("/account");
  }

  const handleFamily = () => {
    navigate("/myFamily")
  }

  return (
    <div className="footer-container">
      <button className='footer-left footer-button' onClick={handleGoBack}>
        <IoArrowBackOutline className='footer-icon' />
      </button>
      <button className='footer-menu footer-button' onClick={handleAccount}>
        <IoCardOutline className='footer-icon'
          style={{
            background: 'linear-gradient(93.46deg, #133FDB 0%, rgba(183, 0, 77, 0.3) 103.75%)', WebkitBackgroundClip: 'text', backgroundClip: 'text', WebkitTextFillColor: 'transparent'
          }} />
      </button>

      <button className='footer-home footer-button'>
        <div className='icon-container'>
          <div className='home-circle' onClick={handleMyPage}>
            <IoHome className='footer-icon' />
          </div>
        </div>
      </button>

      <button className='footer-account footer-button' onClick={handleFamily}>
        <IoPeopleSharp className='footer-icon' />
      </button>
      <button className='footer-etc footer-button'>
        <IoEllipsisHorizontal className='footer-icon' />
      </button>
    </div>
  );
}

export default Footer;
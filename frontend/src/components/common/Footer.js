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
    navigate("/");
  }

  const handleAccount = () => {
    navigate("/account");
  }

  const handleFamily = () => {
    navigate("/myFamily")
  }

  return (
    <div className="footer-container">
      <button className='footer-left footer-button'>
        <IoArrowBackOutline className='footer-icon' onClick={handleGoBack} />
      </button>
      <button className='footer-menu footer-button'>
        <IoCardOutline className='footer-icon' onClick={handleAccount}
          style={{
            background: 'linear-gradient(93.46deg, #133FDB 0%, rgba(183, 0, 77, 0.3) 103.75%)', WebkitBackgroundClip: 'text', backgroundClip: 'text', WebkitTextFillColor: 'transparent'
          }} />
      </button>

      <button className='footer-home footer-button'>
        <div className='icon-container'>
          <div className='home-circle'>
            <IoHome className='footer-icon' onClick={handleMyPage} />
          </div>
        </div>
      </button>

      <button className='footer-account footer-button'>
        <IoPeopleSharp className='footer-icon' onClick={handleFamily} />
      </button>
      <button className='footer-etc footer-button'>
        <IoEllipsisHorizontal className='footer-icon' />
      </button>
    </div>
  );
}

export default Footer;
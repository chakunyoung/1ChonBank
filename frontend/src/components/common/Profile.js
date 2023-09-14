import React, { useState } from 'react';
import './Profile.css';
import gradientCircleSvg from 'assets/gradientcircle.svg';
import { useSelector } from 'react-redux';




function Profile() {
    const userNickname = useSelector((state) => state.auth.nickname)
      
  return (
    <div className="profile-container">
        <img src={gradientCircleSvg} alt="Gradient Circle" />
        <div className="parent-container" style={{ display: "flex", flexDirection: "column" }}>
        <div className='image-profile'>안녕하세요</div>
        <div>{userNickname}</div>
      </div>
      </div>

  );
}

export default Profile;
  import React, { useState, useEffect } from 'react';
  import { useSelector } from 'react-redux';
  import './Profile.css';
  import gradientCircleSvg from 'assets/gradientcircle.svg';
  import Char1 from 'assets/char1x4.png';
  import Char2 from 'assets/char2x4.png';
  import Char3 from 'assets/char3x4.png';
  import Char4 from 'assets/char4x4.png';
  import Char5 from 'assets/char5x4.png';
  import Char6 from 'assets/char6x4.png';
  import Char7 from 'assets/char7x4.png';
  import Char8 from 'assets/char8x4.png';

  function Profile() {
    const userNickname = useSelector((state) => state.auth.nickname);
    const [characterImage, setCharacterImage] = useState(null);
    useEffect(() => {
      // 이미지 로드
      const image = new Image();
      image.src = Char1; // 원하는 캐릭터 이미지 경로로 설정
    
      // 이미지가 로드되면 실행할 함수
      image.onload = () => {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
    
        // 정사각형 크기 설정 (맨 위를 기준으로 자르기)
        const size = Math.min(image.width, image.height);
        canvas.width = size;
        canvas.height = size;
    
        // 이미지를 맨 위를 기준으로 정사각형 크기에 맞게 자르기
        ctx.drawImage(image, 0, 0, size, size, 0, 0, size, size);
    
        // 이미지 데이터 URL로 변환
        const croppedImage = canvas.toDataURL('image/png');
    
        // 상태 업데이트
        setCharacterImage(croppedImage);
      };
    }, []);

    return (
      <div className="profile-container">
        <div className='image-area'>
          <img src={gradientCircleSvg} alt="Gradient Circle" className="gradient-circle" />
          <img src={characterImage} alt="Character" className="profile-image" />
        </div>
        <div className="text-container">
          <div>안녕하세요!</div>
          <div className='nickname'>{userNickname}</div>
        </div>
      </div>
    );
  }

  export default Profile;




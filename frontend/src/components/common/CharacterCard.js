import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import "./CharacterCard.css";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";


import Char1 from 'assets/char1x4.png';
import Char2 from 'assets/char2x4.png';
import Char3 from 'assets/char3x4.png';
import Char4 from 'assets/char4x4.png';
import Char5 from 'assets/char5x4.png';
import Char6 from 'assets/char6x4.png';
import Char7 from 'assets/char7x4.png';
import Char8 from 'assets/char8x4.png';


function CharacterCard() {
  const userNickname = useSelector((state) => state.auth.user.nickname);
  const [characterImage, setCharacterImage] = useState(null);
  const characters = [Char1, Char2, Char3, Char4, Char5, Char6, Char7, Char8];
  const [selectedCharacterIndex, setSelectedCharacterIndex] = useState(0);

  useEffect(() => {
    // 이미지 로드
    const image = new Image();
    image.src = characters[selectedCharacterIndex];

    // 이미지가 로드되면 실행할 함수
    image.onload = () => {
      const canvas = document.createElement('canvas');
      const ctx = canvas.getContext('2d');

      // 정사각형 크기 설정 (맨 위를 기준으로 자르기)
      const size = Math.min(image.width, image.height);
      canvas.width = 512; // 원하는 크기로 설정
      canvas.height = 512; // 원하는 크기로 설정

      // 이미지를 맨 위를 기준으로 정사각형 크기에 맞게 자르기
      ctx.drawImage(image, 0, 0, size, size, 0, 0, 512, 512); 

      // 이미지 데이터 URL로 변환
      const croppedImage = canvas.toDataURL('image/png');

      // 상태 업데이트
      setCharacterImage(croppedImage);
    };
  }, [characters, selectedCharacterIndex]);

  // 이전 버튼 핸들러
  const handlePrevCharacter = () => {
    setSelectedCharacterIndex((prevIndex) => {
      return (prevIndex - 1 + characters.length) % characters.length;
    });
  };
  // 다음 버튼 핸들러
  const handleNextCharacter = () => {
    setSelectedCharacterIndex((prevIndex) => {
      return (prevIndex + 1) % characters.length;
    });
  };

  return (
    <div className="charactercard-container">
        <button className='left-button' onClick={handlePrevCharacter}><IoIosArrowDropleft/></button>
      <div className="character-image">
        <img className='charctercard-imagearea' src={characterImage} alt={`Character ${selectedCharacterIndex + 1}`} />
      </div>
      <button className='right-button'onClick={handleNextCharacter}><IoIosArrowDropright/></button>

    </div>
  );
}

export default CharacterCard;

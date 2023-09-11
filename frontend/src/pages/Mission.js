import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Mission.css';

const Mission = () => {
  const [missionTitle, setMissionTitle] = useState('');
  const [missionDescription, setMissionDescription] = useState('');
  const [selectedChild, setSelectedChild] = useState('');
  const [points, setPoints] = useState(''); // 포인트 상태 추가
  const [validationMessage, setValidationMessage] = useState('');

  const handleMissionAssignClick = () => {
    // 미션 부여 로직을 이곳에 추가하세요.
    // missionTitle, missionDescription, selectedChild, points 변수에 현재 입력된 값이 저장되어 있습니다.

    // 포인트 입력값 유효성 검사
    if (points.trim() === '') {
      setValidationMessage('포인트를 입력하세요.');
    } else if (!/^\d+$/.test(points)) {
      setValidationMessage('숫자만 입력하세요.');
    } else if (parseInt(points) % 100 !== 0) {
      setValidationMessage('100단위로 입력하세요.');
    } else {
      // 유효성 검사를 통과한 경우 메시지를 초기화합니다.
      setValidationMessage('');
    }
  };

  return (
    <div className='MissionContainer'>
      <h1>미션 만들기</h1>
      <div>
        <label htmlFor="missionTitle">미션 이름</label><br />
        <input
          type="text"
          id="missionTitle"
          value={missionTitle}
          onChange={(e) => setMissionTitle(e.target.value)}
          className="mission-title-input"
        />
      </div>
      <div>
        <label htmlFor="missionDescription">미션 내용</label><br />
        <textarea
          id="missionDescription"
          value={missionDescription}
          onChange={(e) => setMissionDescription(e.target.value)}
          className="mission-description-input"
          rows={4}
        />
      </div>
      <div>
        <label htmlFor="selectedChild">대상 자녀</label><br />
        <select
          id="selectedChild"
          value={selectedChild}
          onChange={(e) => setSelectedChild(e.target.value)}
          className="selected-child-select"
        >
          <option value="child1">자식 1</option>
          <option value="child2">자식 2</option>
          <option value="child3">자식 3</option>
          {/* 필요한 선택 옵션들을 추가하세요 */}
        </select>
      </div>
      <div>
        <label htmlFor="points">포인트 설정</label><br />
        <input
          type="text"
          id="points"
          value={points}
          onChange={(e) => setPoints(e.target.value)}
          className="points-input"
        />
      </div>
      <div className="validation-message">{validationMessage}</div>
      <div className="mission-button-container">
        <button onClick={handleMissionAssignClick} className="assign-button">
          미션 등록
          <div className="mission-background-box"></div>
        </button>
      </div>
    </div>
  );
};

export default Mission;
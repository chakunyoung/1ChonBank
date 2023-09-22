import React, { useEffect, useState } from 'react';
import './MissionMake.css';
import Footer from 'components/common/Footer'
import { useDispatch, useSelector } from 'react-redux';
import apis from '../../services/api/apis'
import { setFamilyMember } from 'redux/Family';

const MissionMake = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    // 데이터를 가져오는 비동기 함수를 정의합니다.
    const fetchData = async () => {
      try {
        console.log("가족 정보 조회 보냄");
        const response = await apis.get("/api/families");
        console.log(response);
        const data = response.data.data;
        dispatch(setFamilyMember(data));
         // 데이터 로딩이 완료됨을 표시
      } catch (error) {
        console.error('데이터를 가져오지 못했습니다:', error);
         // 데이터 로딩 실패를 표시
      }
    };

    fetchData(); // 데이터 가져오기 함수 호출
  }, []); 

  const [missionTitle, setMissionTitle] = useState('');
  const [missionDescription, setMissionDescription] = useState('');
  const [selectedChild, setSelectedChild] = useState('');
  const [points, setPoints] = useState(''); // 포인트 상태 추가
  const [validationMessage, setValidationMessage] = useState('');

  const familyMember = useSelector((state)=>state.family.familyMember);
  let childMembers = null; 
  if (familyMember) {
    childMembers = familyMember.filter((member) => member.role === 'ROLE_CHILD');
  }
  
  const user = useSelector((state)=>state.auth.user)
  const date = Date.now();

  const familyName = useSelector((state)=>state.family.familyName);
  const data ={
    missionName : missionTitle,
    missionFamilyName:familyName,
    selectedChild: selectedChild,
    missionDescription : missionDescription,
    missionPoint:points,
    missionTerminateDate:date,
    missionStatus: "시작전",
  }

  useEffect(() => {
    if (childMembers && childMembers.length > 0) {
      setSelectedChild(childMembers[0].nickname);
    }
  }, [childMembers]);

  const handleMissionAssignClick = () => {
    // 미션 부여 로직을 이곳에 추가하세요.
    // missionTitle, missionDescription, selectedChild, points 변수에 현재 입력된 값이 저장되어 있습니다.
    console.log(data);
    // 포인트 입력값 유효성 검사
    if (points.trim() === '') {
      setValidationMessage('포인트를 입력하세요.');
    } else if (!/^\d+$/.test(points)) {
      setValidationMessage('숫자만 입력하세요.');
    } else if (parseInt(points) % 100 !== 0) {
      setValidationMessage('100단위로 입력하세요.');
    } else {
      apis.post("/api/missions/make",data)
      .then((response)=>{
        console.log(response);
      });
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

          {childMembers && childMembers.map((member) => (
            <option
              key={member.nickname}
              value={member.nickname}
            >
              {member.nickname}
            </option>
          ))}
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
      <div className='mission-footer'>
      <Footer/>
      </div>
    </div>
  );
};

export default MissionMake;
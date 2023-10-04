import React, { useState, useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { fetchMissions, useFetchMissions } from 'pages/mission/MissionFetchMission';

const MissionCard = () => {
    const [dataExist, setDataExist] = useState(false);
    const dispatch = useDispatch();
    const userType = useSelector((state) => state.auth.user.roles);
    const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동을 처리
    const mission = useSelector((state)=>state.mission.mission)

    const fetchMissions = useFetchMissions();

    useEffect(() => {
        const fetchData = async () => {
          try {
            const missionData = await fetchMissions(userType); // fetchMissions 함수 호출
            if (missionData.data.length !== 0) {
              setDataExist(true);
            }
            console.log(missionData.data.length);
          } catch (error) {
            console.error('데이터 로딩 중 또는 미안해', error);
          }
        };
    
        fetchData();
      }, [userType]);

    // 미션 디테일 페이지로 이동하는 함수
    const handleMissionDetail = (missionId) => {
        // 미션 디테일 페이지로 이동하면서 missionId를 URL 파라미터로 전달
        console.log(missionId);
        navigate(`/missionDetail/${missionId}`);
    };

    return (
        <div>
            <div className='missioncard-listcontainer'>
                {dataExist ? (
                    mission.data.map((mission, index) => (
                        <div key={index}
                            className={
                                mission.missionStatus === "진행중"
                                    ? "missioncard-container-before"
                                    : mission.missionStatus === "완료"
                                        ? "missioncard-container-during"
                                        : mission.missionStatus === "거절"
                                            ? "missioncard-container-rejected"
                                            : ""
                            }
                            onClick={() => handleMissionDetail(mission.missionId)}>
                            <div className='missionname-box'>{mission.missionName}</div>
                            <div className='missiondetail-box'>
                                <div className='missiondetail-point'>{mission.missionPoint}P</div>
                                <div className='missiondetail-status'>{mission.missionStatus}</div>
                            </div>
                        </div>
                    ))
                ) : (
                    <h2>
                        <br/><br/><br/>
                        진행중인 미션이 없습니다.
                        </h2>

                )}
            </div>
        </div>
    );
};

export default MissionCard;

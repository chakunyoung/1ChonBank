import React, { useState, useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import apis from 'services/api/apis';
import { setMission } from 'redux/Mission';
import { useNavigate } from 'react-router-dom';

const MissionCard = () => {
    const [dataExist, setDataExist] = useState(false); 
    const [missionData, setMissionData] = useState([]); 
    const dispatch = useDispatch();
    const userType = useSelector((state) => state.auth.user.roles);
    const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동을 처리

    useEffect(() => {
        const fetchMissions = async () => {
            if (userType === "ROLE_PARENT") {
                try {
                    const response = await apis.get('/api/missions/family-id');
                    const missionData = response.data;
                    console.log(missionData);
                    dispatch(setMission(missionData));
                    setDataExist(true); 
                    setMissionData(missionData);
                    console.log(missionData);
                } catch (error) {
                    console.error('미션 정보를 가져오는 데 실패했습니다. ', error);
                }
            } else {
                try {
                    const response = await apis.get('/api/missions/child-nickname');
                    const missionData = response.data;
                    console.log(missionData);
                    dispatch(setMission(missionData));
                    setDataExist(true); // 데이터가 로드됐음을 표시
                    setMissionData(missionData);
                } catch (error) {
                    console.error('미션 정보를 가져오는 데 실패했습니다. ', error);
                }
            }
        };

        fetchMissions(); // useEffect 내부에서 호출

    }, [dispatch, userType]);

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
                    missionData.data.map((mission, index) => (
                        <div key={index} className={mission.missionStatus === "시작전" ? 'missioncard-container-before' : 'missioncard-container-during'} onClick={() => handleMissionDetail(mission.missionId)}>
                            <h2 className='margin-box'>{mission.missionName}</h2>
                            <div className='margin-box'>
                                <p>{mission.missionPoint}P</p>
                                <p>{mission.missionStatus}</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <div>데이터 로딩 중 또는 미안해</div>
                )}
            </div>
        </div>
    );
};

export default MissionCard;

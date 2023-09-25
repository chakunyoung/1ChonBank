import React, { useState, useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import apis from 'services/api/apis';
import { setMission } from 'redux/Mission';

const MissionCard = () => {
    const [dataExist, setDataExist] = useState(false); 
    const [missionData, setMissionData] = useState([]); 
    const dispatch = useDispatch();
    const userType = useSelector((state) => state.auth.user.roles);


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
    
    return (
        <div>
            <div>
                {dataExist ? (
                    missionData.data.map((mission, index) => (
                        <div key={index} className='missioncard-container'>
                            {/* <div className='missioncard-textarea'> */}
                                <div className='data-container'>
                                    <h2>미션 이름: {mission.missionName}</h2>
                                    <div className='mission-details'>
                                        <p>미션 포인트: {mission.missionPoint}</p>
                                        <p>미션 상태: {mission.missionStatus}</p>
                                    </div>
                                </div>
                            {/* </div> */}
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

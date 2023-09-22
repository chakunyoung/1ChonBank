import React, { useState, useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import apis from 'services/api/apis';
import { setMission } from 'redux/Mission';

const MissionCard = () => {
    const [dataExist, setDataExist] = useState(false); // 데이터가 있는지 여부를 불리언 값으로 표시
    const missions = useSelector((state) => state.mission);
    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.user);
    const userType = useSelector((state) => state.auth.user.roles);

    useEffect(() => {
        const fetchMissions = async () => {
            if (userType === "ROLE_PARENT") {
                try {
                    const response = await apis.get('/api/missions/family-id');
                    const missionData = response.data;
                    console.log(missionData);
                    dispatch(setMission(missionData));
                    setDataExist(true); // 데이터가 로드됐음을 표시
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
                } catch (error) {
                    console.error('미션 정보를 가져오는 데 실패했습니다. ', error);
                }
            }
        };

        fetchMissions(); // useEffect 내부에서 호출

    }, [dispatch, userType]); // dispatch와 userType를 의존성 배열에 추가

    return (
        <div className='missioncard-container'>
            <div className='missioncard-textarea'>
                {dataExist ? (
                    // missions.map((mission, index) => (
                        // <div key={index}>
                        <div>
                            <h2>미션 이름: {missions.missionName}</h2> 
                            <p>미션 기한: {missions.date}</p> 
                        </div>
                    // ))
                ) : (
                    <div>데이터 로딩 중 또는 미안해</div>
                )}
            </div>
        </div>
    );
};

export default MissionCard;

import React, { useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import apis from 'services/api/apis';
import { setMission } from 'redux/Mission';

const MissionCard = () => {
    const missions = useSelector(state => state.mission);
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchMissions = async () => {
            try {
                const response = await apis.get('/api/missions/family-id');
                const missionData = response.data;
                console.log(missionData);
                console.log("미션데이타");
                dispatch(setMission(missionData));
            } catch (error) {
                console.error('미션 정보를 가져오는 데 실패했습니다. ', error);
            }
        };

        fetchMissions(); // useEffect 내부에서 호출

    }, [dispatch]); // dispatch를 의존성 배열에 추가

    return (
        <div className='missioncard-container'>
            <h1>미션 정보</h1>
            <div className='missioncard-textarea'>
                {missions.length > 0 ? (
                    missions.map((mission, index) => (
                        <div key={index}>
                            <h2>미션 이름: {mission.missionName}</h2> 
                            <p>미션 기한: {mission.date}</p> 
                        </div>
                    ))
                ) : (
                    <p>미션 데이터가 없습니다.</p>
                )}
            </div>
        </div>
    );
};

export default MissionCard;

import React, { useEffect } from 'react';
import './MissionCard.css';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import apis from 'services/api/apis';
import { setMission } from 'redux/Mission';

const MissionCard = () => {
    const mission = useSelector(state => state.mission);
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchMission = async () => {
            try {
                const response = await apis.get('/api/missions/family-id'); // 엔드포인트 경로 수정
                const missionData = response.data;
                console.log(missionData);
                console.log("미션데이타");
                dispatch(setMission(missionData));
            } catch (error) {
                console.error('미션 정보를 가져오는 데 실패했습니다. ', error);
            }
        };

        fetchMission(); // useEffect 내부에서 호출

    }, [dispatch]); // dispatch를 의존성 배열에 추가

    return (
        <div className='missioncard-container'>
            <h1>미션 정보</h1>
            <div className='missioncard-textarea'>
                {mission ? (
                    <div>
                        <h2>미션 이름: {mission.missionName}</h2> 
                        <p>미션 설명: {mission.description}</p> 
                    </div>
                ) : (
                    <p>미션 데이터가 없습니다.</p>
                )}
            </div>
        </div>
    );
};

export default MissionCard;

import { useDispatch, useSelector } from 'react-redux';
import './MissionDetail.css';

import React, { useEffect, useState } from 'react'; // useState 추가
import { useNavigate, useParams } from 'react-router-dom';
import apis from "services/api/apis"
import { setMissionData } from 'redux/Mission';
import MissionChildRequestButton from 'pages/mission/MissionChildRequestButton';
import MissionParentResponseButton from 'pages/mission/MissionParentResponseButton';

function MissionDetail() {
    const missionData = useSelector((state) => state.mission.missionData);
    const { missionId } = useParams();
    const role = useSelector((state) => state.auth.user.roles);
    const dispatch = useDispatch();


    useEffect(() => {
        // 비동기 함수를 정의하고 호출
        async function fetchMissionData() {
            try {
                const response = await apis.get(`/api/missions/detail/${missionId}`);
                // 데이터를 가져와 상태 업데이트
                dispatch(setMissionData(response.data.data));
                
            } catch (error) {
                console.error(error);
            }
        }

        fetchMissionData(); // 비동기 함수 호출

    }, [missionId]); // missionId가 변경될 때마다 실행

    return (
        <div>
            {/* missionData가 있을 때만 데이터를 렌더링 */}
            {missionData && (
                <div>
                    <p>내 이름 : {missionData.childName}</p>
                    <p>Mission Name: {missionData.missionName}</p>
                    <p>Mission Description: {missionData.missionDescription}</p>
                    <p>Mission Status: {missionData.missionStatus}</p>
                    {/* 기타 미션 데이터 필드를 추가하세요 */}
                </div>
            )}
            {missionData && role === 'ROLE_PARENT' && missionData.missionStatus==="완료"&& (
                <div>
                    <MissionParentResponseButton />
                </div>
            )}

            {role !== 'ROLE_PARENT' && (
                <div>
                    <MissionChildRequestButton />
                </div>
            )}
        </div>
    );
};

export default MissionDetail;


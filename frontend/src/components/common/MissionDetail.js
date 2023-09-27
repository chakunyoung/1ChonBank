import { useSelector } from 'react-redux';
import './MissionDetail.css';

import React, { useEffect, useState } from 'react'; // useState 추가
import { useNavigate, useParams } from 'react-router-dom';
import apis from "services/api/apis"

function MissionDetail() {
    const mission = useSelector((state) => state.mission.mission.data);
    const { missionId } = useParams();
    const [missionData, setMissionData] = useState(null); // 미션 데이터 상태 추가
    const role = useSelector((state) => state.auth.user.roles);

    useEffect(() => {
        // 비동기 함수를 정의하고 호출
        async function fetchMissionData() {
            try {
                const response = await apis.get(`/api/missions/detail/${missionId}`);
                // 데이터를 가져와 상태 업데이트
                setMissionData(response.data.data);
                console.log(response.data);
            } catch (error) {
                console.error(error);
            }
        }

        fetchMissionData(); // 비동기 함수 호출
    }, [missionId]); // missionId가 변경될 때마다 실행

    const navigate = useNavigate();

    // 자식이 요청 보내기
    const handleChildRequest = () => {
        try {
            const userConfirmed = window.confirm("정말 다 했어요??");

            if (userConfirmed) {
                const response = apis.put(`api/missions/${missionId}`);
                alert("엄마에게 다 했다고 보냈습니다!!");
                navigate("/missionList");
            }

        } catch (error) {
            console.error(error);
        }
    }

    // 자식이 미션 포기하기
    const handleGiveup = () => {
        try {
            const userConfirmed = window.confirm("정말 다 포기하시겠어요??");

            if (userConfirmed) {
                const response = apis.delete(`api/missions/${missionId}`);
                alert("포기 완료 ㅠ^ㅠ");
                navigate("/missionList");
            }

        } catch (error) {
            console.error(error);
        }
    }
    
    // 부모가 미션완료 수락하기 (포인트 지급)
    const handleAcceptMission = () => {
        try {
            const userConfirmed = window.confirm("확인 버튼을 누르시면 포인트 지급이 됩니다.");

            if (userConfirmed) {
                const response = apis.put(`api/missions/refuse/${missionId}`);
                alert("포인트 지급 완료.");
                navigate("/missionList");
            }

        } catch (error) {
            console.error(error);
        }
    }

    // 부모가 미션 반려하기
    const handleRefuseMission = () => {
        try {
            const userConfirmed = window.confirm("확인 버튼을 누르시면 반려됩니다.");

            if (userConfirmed) {
                const response = apis.put(`api/missions/give-money/${missionId}`, missionData.missionPoint);
                alert("반려 완료.");
                navigate("/missionList");
            }

        } catch (error) {
            console.error(error);
        }
    }

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
            {role === 'ROLE_PARENT' ? (
                <div>
                    <button onClick={handleAcceptMission} >
                        수락
                    </button>
                    <button onClick={handleRefuseMission} >
                        거절
                    </button>

                </div>

            ) : (
                <div>
                    <button onClick={handleChildRequest} disabled={missionData && missionData.missionStatus === "완료"}>
                        요청
                    </button>
                    <button onClick={handleGiveup} disabled={missionData && missionData.missionStatus === "완료"}>
                        포기
                    </button>

                </div>
            )}
        </div>
    );
};

export default MissionDetail;


import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { setMissionData, setMissionDataClear } from 'redux/Mission';
import apis from 'services/api/apis';

function MissionChildRequestButton() {

    const { missionId } = useParams();
    const navigate = useNavigate();
    const missionData = useSelector((state) => state.mission.missionData);
    const dispatch = useDispatch();

    // 자식이 요청 보내기
    const handleChildRequest = () => {
        try {
            const userConfirmed = window.confirm("정말 다 했어요??");

            if (userConfirmed) {
                const response = apis.put(`api/missions/${missionId}`);
                console.log(response.data);
                dispatch(setMissionData(response.data));
                alert("엄마에게 다 했다고 보냈습니다!!");
                navigate("/mypage");
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
                dispatch(setMissionDataClear());
                // dispatch(setMissionData(...missionData, missionData.missionStatus=''));
                alert("포기 완료 ㅠ^ㅠ");
                navigate("/mypage");
            }

        } catch (error) {
            console.error(error);
        }
    }


    return (
        <div>
            <button onClick={handleChildRequest} disabled={missionData && missionData.missionStatus === "완료"}>
                요청
            </button>
            <button onClick={handleGiveup} disabled={missionData && missionData.missionStatus === "완료"}>
                포기
            </button>

        </div>
    )
}

export default MissionChildRequestButton;
// useFetchMissions.js
import apis from "services/api/apis";
import { useDispatch } from "react-redux";
import { setMission } from "redux/Mission";

export const useFetchMissions = () => {
  const dispatch = useDispatch();

  const fetchMissions = async (userType) => {
    try {
      const response = await apis.get(
        userType === "ROLE_PARENT"
          ? "/api/missions/family-id"
          : "/api/missions/child-nickname"
      );
      const missionData = response.data;
      dispatch(setMission(missionData));
      return missionData;
    } catch (error) {
      console.error("미션 정보를 가져오는 데 실패했습니다. ", error);
      throw error;
    }
  };

  return fetchMissions;
};

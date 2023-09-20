// axiosInstance.js
import axios from "axios";
import { getAccessTokenAxios, getRefreshTokenAxios, updateAccessTokenAxios } from "./TokenManager";

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
});

// 모든 요청을 가로채서 accessToken을 넣어줌
axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = getAccessTokenAxios();
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    console.log("accessToken이 없습니다.");
    return Promise.reject(error);
  }
);

// 모든 응답을 가로채서 액세스 토큰 갱신 처리를 하는 인터셉터
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    console.log("access token 에러", error);
   
    if (error.response.data.status === 401 || error.response.data.status === 419 || error.response.data.status === 500) {
      originalRequest._retry = true;
      const refreshToken = getRefreshTokenAxios();
      if (refreshToken) {
        try {
          const response = await axios.post("/api/users/reissue", {}, {
            headers: {
              "Authorization": `Bearer ${refreshToken}`,
            }
          });

          if (response.status === 200) {
           
            const newAccessToken = response.data.data["access-token"];
            const newRefreshToken = response.data.data["refresh-token"];
            updateAccessTokenAxios(newAccessToken, newRefreshToken);

            // 새로운 access token으로 요청을 새로 다시 보냄
            originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
            return axiosInstance(originalRequest);
          }
        } catch (refreshError) {
          console.error("액세스 토큰 갱신 실패:", refreshError);
        }
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
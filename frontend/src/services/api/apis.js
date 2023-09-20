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
    console.log("액세스 토큰 확인", accessToken);
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

    if (error.response.status === 401 && error.response.status === 419) {
      console.log("access token err");
      originalRequest._retry = true;
      const refreshToken = getRefreshTokenAxios();
      if (refreshToken) {
        try {
          const response = await axios.post("/api/users/reissue", {}, {
            headers: {
              "Authorization": `Bearer ${refreshToken}`,
            }
          });

          response = response.data;

          if (response.status === 200) {
            const newAccessToken = response.data["access-token"];
            const newRefreshToken = response.data["refresh-token"];
            updateAccessTokenAxios(newAccessToken, newRefreshToken);
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
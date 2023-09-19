// axiosInstance.js
import axios from "axios";
import { getAccessTokenAxios } from "./TokenManager";

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

export default axiosInstance;

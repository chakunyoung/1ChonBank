import axios from 'axios';

const apis = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  // 여기에 추가 설정을 추가할 수 있습니다
});

export default apis;

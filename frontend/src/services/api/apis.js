// import axios from 'axios';

// const token = localStorage.getItem('access_token');

// const token = JSON.parse(localStorage.getItem('persist:root'));
// const accessToken = JSON.parse(token.auth).accessToken;

// const apis = axios.create({
//   baseURL: process.env.REACT_APP_API_BASE_URL,
//   headers: {
//     'Content-Type': 'application/json',
//     'Authorization': `Bearer ${accessToken}`
//   }
// }); 

// export default apis;
import axios from 'axios';
// localStorage에서 'persist:root' 항목을 가져옵니다.
const tokenString = localStorage.getItem('persist:root');

// tokenString이 null 또는 undefined인 경우에 대한 예외 처리를 추가합니다.
if (!tokenString) {
  console.error('Token not found in localStorage.');
  // 이곳에서 에러 처리를 할 수 있습니다.
}

// tokenString을 파싱하여 token 객체로 변환합니다.
const token = JSON.parse(tokenString);
console.log("여기 토큰 "+token);
// token 객체에서 accessToken을 추출합니다.
const accessToken = token && token.auth ? JSON.parse(token.auth).accessToken : null;
const apis = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Authorization': accessToken ? `Bearer ${accessToken}` : '', // accessToken이 null인 경우 빈 문자열을 사용합니다.
  }
}); 

export default apis;

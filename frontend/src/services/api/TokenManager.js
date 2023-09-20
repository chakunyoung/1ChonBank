/* eslint-disable */
export const getAccessTokenAxios = () => {
  const accessToken = localStorage.getItem("access-token");
  if (accessToken) {
    return accessToken;
  } else {
    console.log("로그인 필요");
    return null;
  }
};

export const getRefreshTokenAxios = () => {
  const refreshToken = localStorage.getItem("refresh-token");
  if (refreshToken) {
    return refreshToken;
  } else {
    console.log("리프레시 토큰 없음");
    return null;
  }
};

export const updateAccessTokenAxios = (newAccessToken, newRefreshToken) => {
  localStorage.setItem("access-token", newAccessToken);
  localStorage.setItem("refresh-token", newRefreshToken);
};
/* eslint-enable */

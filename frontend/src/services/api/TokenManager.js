export const getAccessTokenAxios = () => {
  const authString = JSON.parse(localStorage.getItem("persist:auth"));
  if (authString) {
    const accessToken = authString.accessToken.replace(/^"|"$/g, "");
    if (authString && accessToken === "") {
      console.log("로그인 필요");
    }
    return accessToken;
  } else {
    console.log("로그인 필요");
    return null;
  }
};

export const getRefreshTokenAxios = () => {
  const authString = JSON.parse(localStorage.getItem("persist:auth"));
  if (authString) {
    const refreshToken = authString.refreshToken.replace(/^"|"$/g, "");
    if (authString && refreshToken === "") {
      console.log("리프레시 토큰 없음");
    }
    return refreshToken;
  } else {
    console.log("리프레시 토큰 없음");
    return null;
  }
};

export const updateAccessTokenAxios = (newAccessToken, newRefreshToken) => {
  console.log("access token 만료, 새로 갱신");
  const authString = JSON.parse(localStorage.getItem("persist:auth"));
  if (authString) {
    localStorage.setItem("persist:auth", JSON.stringify({ ...authString, accessToken: newAccessToken, refreshToken: newRefreshToken }));
  } else {
    console.log("update Access token err");
  }
}
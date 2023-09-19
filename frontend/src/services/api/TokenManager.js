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

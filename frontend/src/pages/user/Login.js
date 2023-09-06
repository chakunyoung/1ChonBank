
const Login = () => {

    const onClickKakaoLogin = (e) => {
        window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code`;
    };

    return (
        <button
            style={{
                // background: `url(${process.env.PUBLIC_URL + "/kakao_login_medium_narrow.png"}) no-repeat center center`,
                width: '200px', // 버튼의 너비 설정 (원하는 값으로 조정 가능)
                height: '50px'  // 버튼의 높이 설정 (원하는 값으로 조정 가능)
            }}
            alt="kakao_login"
            onClick={onClickKakaoLogin}
        >
        </button>
    );
}

export default Login;
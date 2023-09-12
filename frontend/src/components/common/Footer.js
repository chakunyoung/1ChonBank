import { Link, useNavigate } from "react-router-dom";

function Footer(){

    const navigate = useNavigate();

    const handleBack=()=>{
        navigate(-1)
    }

    return(
        <div>
            <Link to={"/"}>
                홈
            </Link>
            <Link to={"/"}>
                계좌정보
            </Link>
            <Link to={"/"}>
                가족
            </Link>
            <Link to={"/"}>
                미션
            </Link>
            <button onClick={handleBack}>
                뒤로가기
            </button>
        </div>
    );
};

export default Footer;


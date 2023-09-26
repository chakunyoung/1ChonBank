import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { BsBank, BsFillHousesFill } from "react-icons/bs";
import { AiFillDollarCircle } from "react-icons/ai";
import { TbTargetArrow } from "react-icons/tb";
import { RiQuestionnaireFill } from "react-icons/ri";
import Footer from "components/common/Footer";
import Profile from "components/common/Profile";
import './Mypage.css';
import { useDispatch, useSelector } from 'react-redux';
import Circle from 'components/common/Circle';
import HalfCircleRight from 'components/common/HarfCircleRight';
import apis from 'services/api/apis';
import { getFirebaseToken } from 'services/api/FirebaseAPI';
import { setFirebaseToken, setUser } from 'redux/Auth';

const Mypage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const user = useSelector((state) => state.auth.user);
  const firebaseToken = useSelector((state) => state.auth.firebaseToken);

  const checkQuiz = () => {
    if (user.quiz) alert("오늘은 이미 푸셨습니다.");
    else {
      navigate("/quiz");
    }
  }

  const familyName = useSelector((state) => state.family.familyName);
  const handleCheckHaveFamily = () => {
    navigate("/myFamily");
  }

  useEffect(() => {
    const handleClick = async (event) => {
      if (firebaseToken === "" || firebaseToken === undefined) { 
        const token = await getFirebaseToken();
        if (token) {
          dispatch(setFirebaseToken(token)); 
        }
      }
    };

    document.body.addEventListener('click', handleClick);
    return () => {
      document.body.removeEventListener('click', handleClick);
    };
  }, [firebaseToken]);

  const sendPinMoneyRequest = async () => {
    try {
      const response = await apis.post('/api/banking/pinmoney', {
        childNickname: user.nickname,
        pinMoney: 10000,
        receiveTime: '2023-09-20', // 날짜 형식에 따라 변경
      });

      if (response.status === 200) {
        console.log('요청 성공:', response.data);
        return response.data;
      } else {
        console.error('요청 실패:', response);
        throw new Error('요청 실패');
      }
    } catch (error) {
      console.error('오류 발생:', error);
      throw error;
    }
  };

  return (
    <div className='MypageContainer'>

      <div className='mypage-profilecontainer'>
        <Profile />
      </div>

      <div className="button-grid">
        <div className="row">
          <Link to="/account" className="button button-account">
            <AiFillDollarCircle className='logo' />
            <span>계좌정보</span>
          </Link>
          <div onClick={handleCheckHaveFamily} className="button button-family">
            <BsFillHousesFill className='logo' />
            <span>가족</span>
          </div>
        </div>
        <div className="row">
          <Link to="/financial" className="button button-financial">
            <BsBank className='logo' />
            <span>금융상품</span>
          </Link>
          <Link to="/missionList" className="button button-mission">
            <TbTargetArrow className='logo' />
            <span>미션</span>
          </Link>
        </div>
        <div className="button button-quiz" style={{ width: 340, height: '70px' }} onClick={checkQuiz}>
          <RiQuestionnaireFill className='logo' />
          <span>오늘의 퀴즈</span>
        </div>

        <div className="button button-quiz" style={{ width: 340, height: '70px' }} onClick={sendPinMoneyRequest}>
          <RiQuestionnaireFill className='logo' />
          <span>test</span>
        </div>
      </div>
      <div className='mypage-footer'><Footer /></div>
    </div>
  );
};

export default Mypage;

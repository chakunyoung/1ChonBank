
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { BsBank, BsFillHousesFill } from "react-icons/bs";

import { AiFillDollarCircle } from "react-icons/ai";
import { TbTargetArrow } from "react-icons/tb";
import { RiQuestionnaireFill } from "react-icons/ri";
import Footer from "components/common/Footer";
import Profile from "components/common/Profile"
import './Mypage.css';
import { useSelector } from 'react-redux';
const Mypage = () => {

  const navigate= useNavigate();
  const user = useSelector((state) => state.auth.user);
  
  const checkQuiz = () =>{
    if(user.quiz) alert("오늘은 이미 푸셨습니다.");
    else{
      navigate("/quiz");
    }
  }

  const familyName = useSelector((state) => state.family.familyName);
  const handleCheckHaveFamily = () => {
    if (familyName === null) {
      navigate("/createFamily");
    } else {
      navigate("/myFamily");
    }

  }

  return (
    <div className='MypageContainer'>
      <div className='mypage-profilecontainer'>
      <Profile/>
      </div>
      <div className="button-grid">
        <div className="row">
          <Link to="/account" className="button button-account">
            <AiFillDollarCircle className='logo'/>
            <span>계좌정보</span>
          </Link>
          <div onClick={handleCheckHaveFamily} className="button button-family">
            <BsFillHousesFill className='logo'/>
            <span>가족</span>
          </div>
        </div>
        <div className="row">
          <Link to="/financial" className="button button-financial">
            <BsBank className='logo'/>
            <span>금융상품</span>
          </Link>
          <Link to="/mission" className="button button-mission">
            <TbTargetArrow className='logo'/>
            <span>미션</span>
          </Link>
        </div>
        <div className="button button-quiz" style={{ width: 340, height: '70px' }} onClick={checkQuiz}>
          <RiQuestionnaireFill className='logo'/>
          <span>오늘의 퀴즈</span>
        </div>
      </div>
      <div className='mypage-footer'><Footer/></div>
    </div>
  );
};

export default Mypage;


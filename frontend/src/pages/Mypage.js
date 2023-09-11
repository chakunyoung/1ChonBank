import React from 'react';
import { Link } from 'react-router-dom';
import { BsBank, BsFillHousesFill } from "react-icons/bs";
import { AiFillDollarCircle } from "react-icons/ai";
import { TbTargetArrow } from "react-icons/tb";
import { RiQuestionnaireFill } from "react-icons/ri";

import './Mypage.css';

const Mypage = () => {


  return (
    <div className='MypageContainer'>
        <div className='profile-container'>
            <div className='image-profile'>Good morning</div>
            <div>nickname</div>
        </div>
      <div className="button-grid">
        <div className="row">
          <div className="button account">
            <Link to="/account">계좌정보</Link>
            <AiFillDollarCircle className='logo'/>
          </div>
          <div className="button family">
            <Link to="/family">가족</Link>
            <BsFillHousesFill className='logo'/>
          </div>
        </div>
        <div className="row">
          <div className="button financial">
            <Link to="/financial">금융상품</Link>
            <BsBank className='logo'/>
          </div>
          <div className="button mission">
            <Link to="/mission">미션</Link>
            <TbTargetArrow className='logo'/>
          </div>
        </div>
        <div className="button quiz" style={{ width: 340, height: '70px' }}>
          <Link to="/quiz">퀴즈 <RiQuestionnaireFill className='logo'/></Link>

        </div>
      </div>
    </div>
  );
};
export default Mypage;


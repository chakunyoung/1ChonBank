import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Footer from "components/common/Footer";
import Profile from "components/common/Profile"
import Myaccount from "components/common/Myaccount"
import AccountDeatil from './AccountDetail';
import './Account.css';
import { useSelector } from 'react-redux';
import Card from 'components/banking/Card'


const Account = () => {

  const navigate = useNavigate(); // React Router의 useNavigate 훅을 사용

  const goToDetail = () => {
    navigate("/accountDetail"); // AccountDetail 페이지로 이동
  };

  return (
    <div className='account-container'>
      <div className='account-profilecontainer'>
        <Profile />
      </div>
      <div>
        <Myaccount />
      </div>
      <div className='card-margin' onClick={goToDetail}> {/* onClick 이벤트 추가 */}
        <Card />
      </div>
      <div className='card-margin' onClick={goToDetail}> {/* onClick 이벤트 추가 */}
        <Card />
      </div>
      <div className='account-footer'>
        <Footer />
      </div>

    </div>
  );
};

export default Account;


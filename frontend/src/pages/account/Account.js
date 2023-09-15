import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Footer from "components/common/Footer";
import Profile from "components/common/Profile"
import Myaccount from "components/common/Myaccount"
import AccountDeatil from './AccountDetail';
import './Account.css';
import { useSelector } from 'react-redux';



const Account = () => {


  return (
    <div className='account-container'>
      <div className='account-profilecontainer'>
      <Profile/>
      </div>
      <div>
        <Myaccount/>
      </div>
      <Link to="/accountdetail">detail</Link>
      <div className='account-footer'><Footer/></div>
    </div>
  );
};

export default Account;


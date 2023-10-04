import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Myaccount.css';
import { useSelector } from 'react-redux';

const Myaccount = () => {
  const money = useSelector((state) => state.auth.user.money)

    return (
      <div className='myaccount-container'>
        <div className='myaccount-textarea' >
            <div className='myaccount-total'>Total</div>
            <div className='myaccount-date'>Transaction date</div>
        </div>
        <div className='myaccount-balance'>
            {money}
        </div>
        <></>


        </div>
    );
  };
  
  export default Myaccount;
  
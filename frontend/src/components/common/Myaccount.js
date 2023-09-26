import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Myaccount.css';
import { useSelector } from 'react-redux';

const Myaccount = () => {


    return (
      <div className='myaccount-container'>
        <div className='myaccount-textarea' >
            <div className='myaccount-total'>Total</div>
            <div className='myaccount-date'>Transaction date</div>
        </div>
        <div className='myaccount-balance'>
            3000$ {/* 잔액넣으셈 */}
        </div>
        <></>


        </div>
    );
  };
  
  export default Myaccount;
  
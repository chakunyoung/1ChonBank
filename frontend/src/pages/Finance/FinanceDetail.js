import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {  } from "redux/Depositor";
import {  } from "redux/Savingser";
import {  } from "redux/Loaner";
import './FinanceDetail.css';
const FinanceDetail = () => {
    const [cnt, setCnt] = useState(0);
    const finance = useSelector((state) => state.finance);
    const setAllData = () =>{

    }
    useEffect(setAllData);
    const apply = () => {
        console.log(cnt);
        const a = cnt + 1;
        setCnt(a);
    };
    return (
        <div className='product-detail-container'>
            <div></div>
            {console.log(finance)}
            <div className='product-apply-button' onClick={apply}>신청 하기</div>
        </div>
    );
};

export default FinanceDetail;

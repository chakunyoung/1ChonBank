import React, { useEffect, useState } from 'react';
import { getProductList } from "redux/Finance";
import './FinanceList.css';
import { useDispatch } from 'react-redux';
import FinancelistSet from 'components/Finance/FinancelistSet';
import { Link } from 'react-router-dom';
const FinanceList = () => {
    const [products, setProducts] = useState([]);
    const [viewType, setViewType] = useState('');

    const dispatch = useDispatch();
    const getListAll = () => {
        dispatch(getProductList())
            .then((resultAction) => {
                if (getProductList.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const productList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    setProducts(productList.data);
                } else if (getProductList.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                }
            });
    };
    useEffect(getListAll, []);
    const handleType = (data) => {
        if (viewType == data)
            setViewType('');
        else
            setViewType(data);
    };
    return (
        <div className='list-container'>
            <div className='product-button-list'>
                <button className='productType-button' onClick={() => handleType('DEPOSIT')}>
                    <img className='product-img' src = {require('./deposit.jpg')}/>예금</button>
                <button className='productType-button' onClick={() => handleType('SAVINGS')}>
                    <img className='product-img' src = {require('./savings.jpg')}/>적금</button>
                <button className='productType-button'  onClick={() => handleType('LOAN')}>
                    <img className='product-img' src = {require('./loan.jpg')}/>대출</button>
                <Link to="/selectFinance">
                    <button className='productType-button'>
                    <img className='product-img' src = {require('./Vector.jpg')}/>
                        생성
                    </button>
                </Link>
            </div>
            <FinancelistSet products={products} viewType={viewType}></FinancelistSet>
        </div>
    );
};

export default FinanceList;

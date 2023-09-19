import React, { useEffect, useState } from 'react';
import { getProductList } from "redux/Finance";
import './FinanceList.css';
import { useDispatch, useSelector } from 'react-redux';
import FinancelistSet from 'components/finance/FinancelistSet';
import { Link } from 'react-router-dom';
const FinanceList = () => {
    const [products, setProducts] = useState([]);
    const [viewType, setViewType] = useState('');
    const [test, setTest] = useState(0);
    const role = useSelector((state) => state.auth.roles);
    
    const dispatch = useDispatch();
    const getListAll = () => {
        dispatch(getProductList(1))
            .then((resultAction) => {
                if (getProductList.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const productList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    setProducts(productList.data);
                } else if (getProductList.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    };
    useEffect(getListAll,[test]);
    const handleType = (data) => {
        if (viewType === data)
            setViewType('');
        else
            setViewType(data);
    };
    return (
        <div className='list-container'>
            <div className='product-button-list'>
                <button className='productType-button' onClick={() => handleType('DEPOSIT')}>
                    <img className='product-img' src={require('assets/deposit.jpg')} alt="예금" />예금</button>
                <button className='productType-button' onClick={() => handleType('SAVINGS')}>
                    <img className='product-img' src={require('assets/savings.jpg')} alt="적금" />적금</button>
                <button className='productType-button' onClick={() => handleType('LOAN')}>
                    <img className='product-img' src={require('assets/loan.jpg')} alt="대출" />대출</button>
                {role === 'ROLE_PARENT' ? <Link to="/selectFinance"> {/*부모인지 판별 넣어야됨*/}
                    <button className='productType-button'>
                        <img className='product-img' src={require('assets/Vector.jpg')} alt="추가" />
                        생성
                    </button>
                </Link> : null}

            </div>
            <FinancelistSet products={products} viewType={viewType}></FinancelistSet>
        </div>
    );
};

export default FinanceList;

import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { makeProduct, setName, setInfo, setPeriod, setRate } from "redux/Finance";
import Footer from "components/common/Footer"
import './MakeFinance.css';

const SelectFinance = () => {
    const [productTitle, setProductTitle] = useState('');
    const [productPeriod, setProductPeriod] = useState('');
    const [productRate, setProductRate] = useState('');
    const [productDescription, setProductDescription] = useState('');
    const [validationMessage, setValidationMessage] = useState('');
    const dispatch = useDispatch();
    const nav = useNavigate();
    const handleProductNameChange = (event) => {
        setProductTitle(event.target.value);
        dispatch(setName(event.target.value));
    };
    const handlePeriodChange = (event) => {
        setProductPeriod(event.target.value);
        dispatch(setPeriod(event.target.value));
    };
    const handleRateChange = (event) => {
        setProductRate(event.target.value);
        dispatch(setRate(event.target.value));
    };
    const handleInfoChange = (event) => {
        setProductDescription(event.target.value);
        dispatch(setInfo(event.target.value));
    };
    const finance = useSelector((state) => state.finance.data);
    const handlemakeProduct = () => {
        if (productTitle === '' || productPeriod === '' || productRate === '' || productDescription === ''){
            setValidationMessage('입력 정보가 부족합니다.');
            return;
        }
        if (productDescription.length < 20){
            setValidationMessage('상품 설명이 부족합니다.');
            return;
        }
        if (productPeriod > 1000 || productRate > 1000){
            setValidationMessage(`기간이나 ${productRateName}을(를) 줄여 주세요.`);
            return;
        }
        dispatch(makeProduct(finance))
            .then((resultAction) => {
                console.log(finance);
                if (makeProduct.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    nav("/financial");
                } else if (makeProduct.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }

    let productType = useSelector((state) => state.finance.productType);
    let productTypeName = '';
    let productRateName = '';
    switch (productType) {
        case 'DEPOSIT':
            productTypeName = '예금'
            productRateName = '이율'
            break;
        case 'SAVINGS':
            productTypeName = '적금'
            productRateName = '이율'
            break;
        case 'LOAN':
            productTypeName = '대출'
            productRateName = '이자'
            break;
    }
    return (
        <div className='make-product-container'>
            <h1>{productTypeName} 상품 생성</h1>
            <div className='title-info-set'>
                <label htmlFor="productTitle">상품 이름</label><br />
                <input
                    id="productTitle"
                    type="text"
                    value={productTitle}
                    onChange={handleProductNameChange}
                    className="product-title-input"
                />
            </div>
            <div className='title-info-set'>
                <label htmlFor="productPeriod">계약 기간 ( 개월 )</label><br />
                <input
                    id="productPeriod"
                    type="number"
                    value={productPeriod}
                    onChange={handlePeriodChange}
                    className="product-title-input"
                />
            </div>
            <div className='title-info-set'>
                <label htmlFor="productRate">{productRateName}이자율 ( % )</label><br />
                <input
                    id="productRate"
                    type="number"
                    value={productRate}
                    onChange={handleRateChange}
                    className="product-title-input"
                />
            </div>
            <div className='title-info-set'>
                <label htmlFor="productDescription">상품 설명</label><br />
                <textarea
                    id="productDescription"
                    onChange={handleInfoChange}
                    value={productDescription}
                    className="product-description-input"
                />
            </div>
            <div className="validation-message">{validationMessage}</div>
            <div className="make-button-container">
                <button className="make-button" onClick={handlemakeProduct}>
                    만들기
                </button>
            </div>
            <div className='selectfinance-footer'><Footer/></div>
        </div>
    );
};

export default SelectFinance;

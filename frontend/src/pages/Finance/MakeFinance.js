import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { makeProduct, setName, setInfo, setPeriod, setRate } from "redux/Finance";
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
    let user = useSelector((state) => state.auth);
    let finance = useSelector((state) => state.finance);
    const handlemakeProduct = () => {
        if (productTitle == '' || productPeriod == '' || productRate == '' || productDescription.length < 10){
            setValidationMessage('입력 정보가 부족합니다.');
            return;
        }
        dispatch(makeProduct(finance))
            .then((resultAction) => {
                if (makeProduct.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    nav("/");
                } else if (makeProduct.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
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
        default:
            nav("/");
    }
    return (
        <div className='make-container'>
            <p>{productTypeName} 상품 생성</p>
            <div>
                <label htmlFor="productTitle">상품 이름</label><br />
                <input
                    id="productTitle"
                    type="text"
                    value={productTitle}
                    onChange={handleProductNameChange}
                    className="product-title-input"
                />
            </div>
            <div>
                <label htmlFor="productPeriod">계약 기간 ( 개월 )</label><br />
                <input
                    id="productPeriod"
                    type="number"
                    value={productPeriod}
                    onChange={handlePeriodChange}
                    className="product-title-input"
                />
            </div>
            <div>
                <label htmlFor="productRate">{productRateName} ( % )</label><br />
                <input
                    id="productRate"
                    type="number"
                    value={productRate}
                    onChange={handleRateChange}
                    className="product-title-input"
                />
            </div>
            <div>
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
                <div className="background-box"></div>
            </div>
        </div>
    );
};

export default SelectFinance;

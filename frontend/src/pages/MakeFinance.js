import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { makeProduct, setName, setInfo, setPeriod, setRate } from "redux/Finance";
import './MakeFinance.css';

const SelectFinance = () => {
    const dispatch = useDispatch();
    const nav = useNavigate();
    const handleProductNameChange = (event) => {
        dispatch(setName(event.target.value));
    };
    const handlePeriodChange = (event) => {
        dispatch(setPeriod(event.target.value));
    };
    const handleRateChange = (event) => {
        dispatch(setRate(event.target.value));
    };
    const handleInfoChange = (event) => {
        dispatch(setInfo(event.target.value));
    };
    const makeProducts = () =>{
        console.log(1111);
        dispatch(makeProduct())
        .then((resultAction) => {
            if (makeProduct.fulfilled.match(resultAction)) {
                // 성공적으로 완료됐을 때
                const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                console.log('Product created:', product);
              } else if (makeProduct.rejected.match(resultAction)) {
                // 작업이 실패했을 때
                const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                console.error('Failed to create product:', error);
              }
        });
    }

    let productType = useSelector((state) => state.finance.productType);
    let productTypeName = '';
    let productRateName = '';
    console.log(productType);
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
                    onChange={handleProductNameChange}
                    className="product-title-input"
                />
            </div>
            <div>
                <label htmlFor="productPeriod">계약 기간</label><br />
                <input
                    id="productPeriod"
                    type="number"
                    onChange={handlePeriodChange}
                    className="product-title-input"
                />
            </div>
            <div>
                <label htmlFor="productRate">{productRateName}</label><br />
                <input
                    id="productRate"
                    type="number"
                    onChange={handleRateChange}
                    className="product-title-input"
                />
            </div>
            <div>
                <label htmlFor="productDescription">상품 설명</label><br />
                <textarea
                    id="productDescription"
                    onChange={handleInfoChange}
                    className="product-description-input"
                />
            </div>
            <div className="make-button-container">
                <button className="make-button" onClick={makeProducts}>
                    만들기
                </button>
                <div className="background-box"></div>
            </div>
        </div>
    );
};

export default SelectFinance;

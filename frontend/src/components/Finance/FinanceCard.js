import React from 'react';
import './FinanceCard.css';

const FinanceCard = (props) => {
    return (
        <div className='card-container'>
            <img className='card-product-img' src={props.product.productType === 'DEPOSIT' ? require('assets/deposit.jpg') : props.product.productType === 'SAVINGS' ? require('assets/savings.jpg') : require('assets/loan.jpg')} alt="상품" />
            <div className='product-info'>
                <div className='product-name'>
                    <div>{props.product.name}</div>
                </div>
                <div className='product-etc'>
                    <div>기간 : {props.product.period} 개월 </div>
                    <div>이율 : {props.product.rate} %</div>
                </div>
            </div>
        </div>
    );
};
export default FinanceCard;


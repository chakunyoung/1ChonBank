import React from 'react';
import './FinanceCard.css';
import Deposit from 'assets/deposit.svg';
import Savings from 'assets/savings.svg';
import Loan from 'assets/loan.svg';

const FinanceCard = (props) => {
    return (
        <div className='finance-card-container'>
            <img className='card-product-img' src={props.product.productType === 'DEPOSIT' ? Deposit : props.product.productType === 'SAVINGS' ? Savings : Loan} alt="상품" />
            <div className='product-info'>
                <div className='product-name'>
                    <div>{props.product.name}</div>
                    {props.cnt !== 0 ? <div className='apply-alarm'></div> : null}
                </div>
                <div className='product-etc'>
                    <div>기간 : {props.product.period}개월</div>
                    <div>이자율 : {props.product.rate}%</div>
                </div>
            </div>
        </div>
    );
};
export default FinanceCard;


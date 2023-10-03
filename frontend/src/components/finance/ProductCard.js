import React from 'react';
import './FinanceCard.css';
import Deposit from 'assets/deposit.svg';
import Savings from 'assets/savings.svg';
import Loan from 'assets/loan.svg';

const ProductCard = (props) => {
    return (
        <div className='finance-card-container'>
            <img className='card-product-img' src={props.pType === 'DEPOSIT' ? Deposit : props.pType === 'SAVINGS' ? Savings : Loan} alt="상품" />
            <div className='product-info'>
                <div className='product-name'>
                    <div>{props.product.cardNumber}</div>
                </div>
                <div className='product-etc'>
                    상품명 : <div>{props.product.productName}</div>
                    금 액 : <div>{props.product.money}</div>
                </div>
                <div className='product-etc'>
                    만기일 : <div>{props.product.expiry}</div>
                </div>
            </div>
        </div>
    );
};
export default ProductCard;


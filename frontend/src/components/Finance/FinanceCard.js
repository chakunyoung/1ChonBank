import React from 'react';
import './FinanceCard.css';

const FinanceCard = (props) => {
    return (
        <div className='card-container'>
            <div>{props.product.productType}</div>
            <div>{props.product.name}</div>
            <div>{props.product.period}</div>
            <div>{props.product.rate}</div>
            <div>{props.product.info}</div>
        </div>
    );
};
export default FinanceCard;


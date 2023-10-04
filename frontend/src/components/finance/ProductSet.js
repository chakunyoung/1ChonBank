import React from 'react';
import './FinancelistSet.css';
import ProductCard from './ProductCard';
import { useNavigate } from 'react-router-dom';

const ProductSet = (props) => {
    const navigate = useNavigate();

    const handleCardClick = (data) => {
        navigate('/accountDetail', { state: { data } });
    };

    const rendering = () => {
        let result = [];
        const depositors = props.depositors;
        console.log(depositors);
        for (let i = 0; i < depositors.length; i++) {
            const p = depositors[i];
            result.push(
                <div
                    className='card-product-box'
                    key={depositors[i].id + ' ' + depositors[i].financialProductId}
                    onClick={() => handleCardClick(p)}
                >
                    <ProductCard product={p} pType='DEPOSIT'></ProductCard>
                </div>
            );
        }
        const loaners = props.loaners;
        for (let i = 0; i < loaners.length; i++) {
            const p = loaners[i];
            result.push(
                <div
                    className='card-product-box'
                    key={loaners[i].id + ' ' + loaners[i].financialProductId}
                    onClick={() => handleCardClick(p)}
                >
                    <ProductCard product={p} pType='LOANERS'></ProductCard>
                </div>
            );
        }
        const savings = props.savings;
        for (let i = 0; i < savings.length; i++) {
            const p = savings[i];
            result.push(
                <div
                    className='card-product-box'
                    key={savings[i].id + ' ' + savings[i].financialProductId}
                    onClick={() => handleCardClick(p)}
                >
                    <ProductCard product={p} pType='SAVINGS'></ProductCard>
                </div>
            );
        }
        return result;
    };

    return (
        <div className='list-set-container'>
            <div className='tab-productlist'>
                {rendering()}
            </div>
        </div>
    );
};

export default ProductSet;

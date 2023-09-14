import React, { useEffect, useState } from 'react';
import FinanceCard from './FinanceCard';
import './FinancelistSet.css';
import { setAll } from "redux/Finance";
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const FinancelistSet = (props) => {
    const [cnt, setCnt] = useState(5);
    const [type, setType] = useState('');
    const dispatch = useDispatch();
    const nav = useNavigate();
    useEffect(()=>{
    	setType(props.viewType);
    }, [props.viewType]);
    useEffect(()=>{
    	setCnt(5);
    },[type]);
    const handlerFinanceDetail = (product) => {
        dispatch(setAll({id: product.id, parentId: product.parentId, familyId: product.familyId, name: product.name, rate: product.rate, info: product.info, period: product.period, productType: product.productType}));
        nav("/financeDetail");
    }
    const rendering = () => {
        const tester = props.products;
        const result = [];
        let check = 0;
        let i = 0;
        for (; i < tester.length && check < cnt; i++) {
            const p = tester[i];
            if (type === '') {
                result.push(<div className='card-product-box' key={tester[i].id} onClick={() => handlerFinanceDetail(p)}><FinanceCard product={p}></FinanceCard></div>);
                check++;
            }
            else if (tester[i].productType === type) {
                result.push(<div className='card-product-box' key={tester[i].id} onClick={() => handlerFinanceDetail(p)}><FinanceCard product={p}></FinanceCard></div>);
                check++;
            }
        }
        if (tester.length !== i)
                result.push(<button key="add-button" className='card-add-button' onClick={addProduct}>...</button>);
        return result;
    };
    const addProduct = () => {
        setCnt(cnt + 5);
    };
    return (
        <div className='list-set-container'>
            {rendering()}
        </div>
    );
};
export default FinancelistSet;


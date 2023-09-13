import React, { useEffect, useState } from 'react';
import FinanceCard from './FinanceCard';

const FinancelistSet = (props) => {
    const [cnt, setCnt] = useState(5);
    const [type, setType] = useState('');
    useEffect(()=>{
    	setType(props.viewType);
    });
    useEffect(()=>{
    	setCnt(5);
    },[type]);
    const rendering = () => {
        const tester = props.products;
        const result = [];
        let check = 0;
        let i = 0;
        for (; i < tester.length && check < cnt; i++) {
            if (type == '') {
                result.push(<div key={tester[i].id}><FinanceCard product={tester[i]}></FinanceCard></div>);
                check++;
            }
            else if (tester[i].productType == type) {
                result.push(<div key={tester[i].id}><FinanceCard product={tester[i]}></FinanceCard></div>);
                check++;
            }
        }
        if (tester.length != i)
                result.push(<button key="add-button" onClick={addProduct}>추가</button>);
        return result;
    };
    const addProduct = () => {
        setCnt(cnt + 5);
    };
    return (
        <div className='list-set-container'>
            <div>{rendering()}</div>
            
        </div>
    );
};
export default FinancelistSet;


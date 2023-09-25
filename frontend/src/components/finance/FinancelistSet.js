import React, { useEffect, useState } from 'react';
import FinanceCard from './FinanceCard.js';
import './FinancelistSet.css';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const FinancelistSet = (props) => {
    const [cnt, setCnt] = useState(5);
    const [type, setType] = useState(''); // 예시: type 상태 정의

    const [activeTab, setActiveTab] = useState('all'); // 기본 탭을 'all'로 설정
    const dispatch = useDispatch();
    const nav = useNavigate();

    useEffect(() => {
        setType(props.viewType);
    }, [props.viewType]);

    useEffect(() => {
        setCnt(5);
    }, [activeTab]); // 탭이 변경될 때마다 cnt 초기화

    const handlerFinanceDetail = (product) => {
        nav(`/financeDetail/${product.id}`);
    }

    const rendering = () => {
        const tester = props.products;
        const result = [];
        let check = 0;

        for (let i = 0; i < tester.length && check < cnt; i++) {
            const p = tester[i];
            const applys = tester[i].productType === 'DEPOSIT' ? props.dapplys : tester[i].productType === 'SAVINGS' ? props.sapplys : props.lapplys;
            let needarr = [];

            for (let index = 0; index < applys.length; index++) {
                if (applys[index].financialProductId === p.id){
                    needarr = needarr.concat([applys[index]]);
                }
            }

            // 모든 탭 또는 선택한 탭에 따라 카드 렌더링
            if (activeTab === 'all' || tester[i].productType === activeTab) {
                result.push(
                    <div className='card-product-box' key={tester[i].id} onClick={() => handlerFinanceDetail(p, needarr)}>
                        <FinanceCard product={p} cnt={needarr.length}></FinanceCard>
                    </div>
                );
                check++;
            }
        }

        if (tester.length !== check) {
            result.push(
                <button key="add-button" className='card-add-button' onClick={addProduct}>...</button>
            );
        }

        return result;
    };

    const addProduct = () => {
        setCnt(cnt + 5);
    };

    return (
        <div className='list-set-container'>
            <div className='tab-container'>
            <div className="tabs">
            </div>
                <button className={activeTab === 'all' ? 'active' : ''} onClick={() => setActiveTab('all')}>전체상품</button>
                <button className={activeTab === 'DEPOSIT' ? 'active' : ''} onClick={() => setActiveTab('DEPOSIT')}>예금</button>
                <button className={activeTab === 'SAVINGS' ? 'active' : ''} onClick={() => setActiveTab('SAVINGS')}>적금</button>
                <button className={activeTab === 'LOAN' ? 'active' : ''} onClick={() => setActiveTab('LOAN')}>대출</button>
            </div>
            <div className='tab-productlist'>
            {rendering()}
            </div>
        </div>
    );
};

export default FinancelistSet;

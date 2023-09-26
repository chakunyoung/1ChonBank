import React, { useEffect, useState } from 'react';
import { getProductList } from "redux/Finance";
import './FinanceList.css';
import { useDispatch, useSelector } from 'react-redux';
import FinancelistSet from 'components/finance/FinancelistSet';
import { Link, useNavigate } from 'react-router-dom';
import { getDepositor } from "redux/Depositor";
import { getSavingser } from "redux/Savingser";
import { getLoaner } from "redux/Loaner";
import { IoIosAdd } from "react-icons/io";
import Footer from "components/common/Footer"
import Profile from "components/common/Profile"


const FinanceList = () => {
    
    const [products, setProducts] = useState([]);
    const [viewType, setViewType] = useState('');
    const [test, setTest] = useState(0);
    const [dapplys, setDapplays] = useState([]);
    const [sapplys, setSapplays] = useState([]);
    const [lapplys, setLapplays] = useState([]);
    const role = useSelector((state) => state.auth.user.roles);
    const dispatch = useDispatch();
    const nav = useNavigate();
    const family = useSelector((state) => state.family.familyName);
    const getListAll = () => {
        // if (family === ''){
        //     alert('가족을 먼저 생성하십시오.');
        //     nav('/');
        //     return;
        // }
        dispatch(getProductList())
            .then((resultAction) => {
                if (getProductList.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const productList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    setProducts(productList.data);
                } else if (getProductList.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
        if (role === 'ROLE_PARENT') {
            dispatch(getDepositor())
                .then((resultAction) => {
                    if (getDepositor.fulfilled.match(resultAction)) {
                        // 성공적으로 완료됐을 때
                        const deposiorList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                        setDapplays(deposiorList.data);
                    } else if (getDepositor.rejected.match(resultAction)) {
                        // 작업이 실패했을 때
                        const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                        console.log(error);
                    }
                });
            dispatch(getSavingser())
                .then((resultAction) => {
                    if (getSavingser.fulfilled.match(resultAction)) {
                        // 성공적으로 완료됐을 때
                        const savingserList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                        setSapplays(savingserList.data);
                    } else if (getSavingser.rejected.match(resultAction)) {
                        // 작업이 실패했을 때
                        const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                        console.log(error);
                    }
                });
            dispatch(getLoaner())
                .then((resultAction) => {
                    if (getLoaner.fulfilled.match(resultAction)) {
                        // 성공적으로 완료됐을 때
                        const loanerList = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                        setLapplays(loanerList.data);
                    } else if (getLoaner.rejected.match(resultAction)) {
                        // 작업이 실패했을 때
                        const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                        console.log(error);
                    }
                });
        }
    };
    useEffect(getListAll, [test]);
    const handleType = (data) => {
        if (viewType === data)
            setViewType('');
        else
            setViewType(data);
    };
    return (
        <div className='finance-list-container'>
            <div><Profile/></div>
            <div>
            <FinancelistSet products={products} dapplys={dapplys} sapplys={sapplys} lapplys={lapplys} viewType={viewType}></FinancelistSet>
            </div>
            <div>
                {role === 'ROLE_PARENT' ? <Link to="/selectFinance" style={{ textDecoration: 'none' }}> {/*부모인지 판별 넣어야됨*/}
                    <button className='make-product-button'>
                    <span className="button-text">상품 만들기</span>
                        <IoIosAdd style={{ fontSize: '40px' }} />
                    </button>
                </Link> : null}
                </div>
        <div className='finance-list-footer'><Footer/></div>
        </div>
    );
};

export default FinanceList;

import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AiOutlineDown, AiOutlineNotification } from 'react-icons/ai';
import { makeDepositor, setDepositorFinancialProductId, getDepCustomer } from "redux/Depositor";
import { makeSavingser, setSavingserFinancialProductId, getSavCustomer } from "redux/Savingser";
import { makeLoaner, setLoanerFinancialProductId, getLoaCustomer } from "redux/Loaner";
import { MdLabelOutline } from "react-icons/md";

import './FinanceDetail.css';
import { useNavigate } from 'react-router-dom';
import Footer from 'components/common/Footer';
import ApplyList from 'components/finance/ApplyList';

const FinanceDetail = () => {
    const [showDiv1, setShowDiv1] = useState(false);
    const [showDiv2, setShowDiv2] = useState(false);
    const [showDiv3, setShowDiv3] = useState(false);
    const [showDiv4, setShowDiv4] = useState(false);
    const [agreePart1, setAgreePart1] = useState(false);
    const [agreePart2, setAgreePart2] = useState(false);
    const [agreePart3, setAgreePart3] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [regularMoney, setRegularMoney] = useState('');
    const finance = useSelector((state) => state.finance.data);
    const depositor = useSelector((state) => state.depositor.data);
    const savingser = useSelector((state) => state.savingser.data);
    const loaner = useSelector((state) => state.loaner.data);
    const role = useSelector((state) => state.auth.user.roles);
    const [applys, setApplys] = useState([]);
    const info = {
        'DEPOSIT': '예금은 금융 기관에 돈을 보관하는 금융 제품으로, 안전하게 자금을 보호하고 미래에 사용할 수 있도록 합니다. 예금은 이자를 얻을 수 있고, 금융 기관은 이러한 예금을 활용하여 대출을 제공하며 경제에 기여합니다. 예금은 금융 계획과 금전 관리에서 중요한 역할을 합니다.',
        'SAVINGS': '적금은 정기적으로 일정 금액을 저축하는 금융 상품으로, 목표 금액을 달성하거나 일정 기간 후에 원금과 이자를 받을 수 있습니다. 이는 금전 관리와 재무 목표 달성을 위한 효과적인 방법으로 사용되며, 안정적인 이자 수입을 제공하여 금융 안전성을 높입니다. 적금은 금융 계획과 금전 관리에서 중요한 역할을 합니다.',
        'LOAN': '대출은 금융 기관으로부터 일정 금액을 빌려오는 금융 거래로, 개인 또는 기업의 자금 필요를 충족하거나 투자를 지원하기 위해 사용됩니다. 대출은 이자를 포함하여 원금 상환을 통해 상환되며, 상환 기간과 이자율은 대출 종류와 조건에 따라 다양합니다.'
    }
    const productName = {
        'DEPOSIT': '예금',
        'SAVINGS': '적금',
        'LOAN': '대출'
    }
    const dispatch = useDispatch();
    const nav = useNavigate();
    const setBase = () => {
        switch (finance.productType) {
            case 'DEPOSIT':
                dispatch(setDepositorFinancialProductId(finance.id));
                if (role === 'ROLE_PARENT') {
                    dispatch(getDepCustomer(finance.id))
                        .then((res) => {
                            if (getDepCustomer.fulfilled.match(res)) {
                                // 성공적으로 완료됐을 때
                                const resList = res.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                                setApplys(resList.data);
                            } else if (getDepCustomer.rejected.match(res)) {
                                // 작업이 실패했을 때
                                const error = res.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                                console.log(error);
                            }
                        });
                }
                break;
            case 'SAVINGS':
                dispatch(setSavingserFinancialProductId(finance.id));
                if (role === 'ROLE_PARENT') {
                    dispatch(getSavCustomer(finance.id))
                        .then((res) => {
                            if (getSavCustomer.fulfilled.match(res)) {
                                // 성공적으로 완료됐을 때
                                const resList = res.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                                setApplys(resList.data);
                            } else if (getSavCustomer.rejected.match(res)) {
                                // 작업이 실패했을 때
                                const error = res.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                                console.log(error);
                            }
                        });
                }
                break;
            case 'LOAN':
                dispatch(setLoanerFinancialProductId(finance.id));
                if (role === 'ROLE_PARENT') {
                    dispatch(getLoaCustomer(finance.id))
                        .then((res) => {
                            if (getLoaCustomer.fulfilled.match(res)) {
                                // 성공적으로 완료됐을 때
                                const resList = res.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                                setApplys(resList.data);
                            } else if (getLoaCustomer.rejected.match(res)) {
                                // 작업이 실패했을 때
                                const error = res.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                                console.log(error);
                            }
                        });
                }
                break;
            default:
                break;
        }
    }
    useEffect(setBase, [finance]);
    const apply = () => {
        if (finance.productType === 'SAVINGS' && regularMoney < 1000) {
            setErrorMessage('금액이 너무 적습니다.');
            setShowDiv4(true);
            return;
        }
        if (agreePart1 && agreePart2 && agreePart3) {
            console.log('감사합니다. 호갱님');
            setErrorMessage('');
            switch (finance.productType) {
                case 'DEPOSIT':
                    makeD()
                    break;
                case 'SAVINGS':
                    makeS();
                    break;
                case 'LOAN':
                    makeL();
                    break;
                default:
                    break;
            }

        } else {
            setErrorMessage('약관의 동의해라 이 자식아!');
            console.log('약관의 동의해라 이 자식아!');
        }
    };
    const makeD = () => {
        dispatch(makeDepositor(depositor))
            .then((resultAction) => {
                if (makeDepositor.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    console.log(product);
                    nav("/financial");
                } else if (makeDepositor.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const makeS = () => {
        dispatch(makeSavingser(savingser))
            .then((resultAction) => {
                if (makeSavingser.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    console.log(product);
                    nav("/financial");
                } else if (makeSavingser.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const makeL = () => {
        dispatch(makeLoaner(loaner))
            .then((resultAction) => {
                console.log(loaner);
                if (makeLoaner.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const product = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    console.log(product);
                    nav("/financial");
                } else if (makeLoaner.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const handleRateChange = (event) => {
        setRegularMoney(Number(event.target.value));
    };
    return (
        <div className='product-detail-container'>
            {regularMoney}
            <div className='product-detail-header'>
                <div className='product-detail-name'>{finance.name}</div>
                <div className='product-summary-infomation'>
                    <img className='card-product-img' src={finance.productType === 'DEPOSIT' ? require('assets/deposit.jpg') : finance.productType === 'SAVINGS' ? require('assets/savings.jpg') : require('assets/loan.jpg')} alt="상품" />
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-rate'></div>
                        <div>{finance.rate} % </div>
                    </div>
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-period'></div>
                        <div>{finance.period} 개월 </div>
                    </div>
                </div>
            </div>
            <div className='product-info-container'>
                <div className='product-space-between'>
                    <span className='option-title'>상품 안내</span>
                    <div className='option-buttons'>
                        {role === 'ROLE_PARENT' ? null : agreePart1 ? <span className='disagree-button' onClick={() => setAgreePart1(!agreePart1)}>동의</span> : <span className='agree-button' onClick={() => setAgreePart1(!agreePart1)}>동의</span>}
                        <span className='show-info-button' onClick={() => setShowDiv1(!showDiv1)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>
                {showDiv1 && (
                    <div className='detail-dropdown'>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 종류 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{productName[finance.productType]}</div>
                        </div>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>계약 기간 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{finance.period} 개월</div>
                        </div>
                        <div className='flex-row'>
                            <   MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 이율 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{finance.rate} %</div>
                        </div>
                    </div>
                )}
                <div className='product-space-between'>
                    <span className='option-title'>가입 정보</span>
                    <div className='option-buttons'>
                        {role === 'ROLE_PARENT' ? null : agreePart2 ? <span className='disagree-button' onClick={() => setAgreePart2(!agreePart2)}>동의</span> : <span className='agree-button' onClick={() => setAgreePart2(!agreePart2)}>동의</span>}
                        <span className='show-info-button' onClick={() => setShowDiv2(!showDiv2)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>
                {showDiv2 && (
                    <div className='detail-dropdown'>
                        <div className='flex-row'>
                            <AiOutlineNotification className='finance-detail-icon' />
                            <div className="text-label join-essential-info">{finance.info}</div>
                        </div>
                    </div>
                )}
                <div className='product-space-between'>
                    <span className='option-title'>{productName[finance.productType]} 설명서 확인</span>
                    <div className='option-buttons'>
                        {role === 'ROLE_PARENT' ? null : agreePart3 ? <span className='disagree-button' onClick={() => setAgreePart3(!agreePart3)}>동의</span> : <span className='agree-button' onClick={() => setAgreePart3(!agreePart3)}>동의</span>}
                        <span className='show-info-button' onClick={() => setShowDiv3(!showDiv3)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>
                {showDiv3 && (
                    <div className='text-label product-common-info'>
                        {info[finance.productType]}
                    </div>
                )}
                {role === 'ROLE_PARENT' ? null : (finance.productType === 'SAVINGS') && <div className='product-space-between'>
                    <span className='option-title'>필수 기입 정보</span>
                    <div>
                        <span className='show-info-button' onClick={() => setShowDiv4(!showDiv4)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>
                }
                {showDiv4 && (
                    <div className='option-input'>
                        <input type="number" value={regularMoney} onChange={handleRateChange}></input>
                    </div>
                )}
                <div className='errorMessage'>{errorMessage}</div>
            </div>

            {role === 'ROLE_PARENT' ? null : <div className='product-apply-button' onClick={apply}>신청 하기</div>}
            {role === 'ROLE_PARENT' ? <ApplyList applys={applys} type={finance.productType} /> : null}
            <div className='finance-detail-footer'>
                <Footer />
            </div>
        </div >
    );
};

export default FinanceDetail;

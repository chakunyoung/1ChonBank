import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AiOutlineDown, AiOutlineUp, AiOutlineNotification } from 'react-icons/ai';
import { getProductInfo } from "redux/Finance";
import { makeDepositor, setDepositorFinancialProductId, getDepCustomer, setDepositorMoney } from "redux/Depositor";
import { makeSavingser, setSavingserFinancialProductId, getSavCustomer, setSavingserRegularMoney } from "redux/Savingser";
import { makeLoaner, setLoanerFinancialProductId, getLoaCustomer, setLoanerMoney } from "redux/Loaner";
import { MdLabelOutline } from "react-icons/md";
import './FinanceDetail.css';
import { useNavigate, useParams } from 'react-router-dom';
import Footer from 'components/common/Footer';
import ApplyList from 'components/finance/ApplyList';
import Deposit from 'assets/deposit.svg';
import Savings from 'assets/savings.svg';
import Loan from 'assets/loan.svg';
import { FaWonSign } from "react-icons/fa";

import Interest from 'assets/interest-rate.png'

const FinanceDetail = () => {
    const [showDiv1, setShowDiv1] = useState(false);
    const [showDiv2, setShowDiv2] = useState(false);
    const [showDiv3, setShowDiv3] = useState(false);
    const [showDiv4, setShowDiv4] = useState(false);
    const [agreePart1, setAgreePart1] = useState(false);
    const [agreePart2, setAgreePart2] = useState(false);
    const [agreePart3, setAgreePart3] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [money, setMoney] = useState('');
    const [finance, setFinance] = useState({});
    const depositor = useSelector((state) => state.depositor.data);
    const savingser = useSelector((state) => state.savingser.data);
    const loaner = useSelector((state) => state.loaner.data);
    const role = useSelector((state) => state.auth.user.roles);
    const params = useParams();
    const [applys, setApplys] = useState([]);
    const [agreements, setAgreements] = useState([false, false, false]); // 각 약관에 대한 동의 상태
    
    const toggleAgreement = (index) => {
        const newAgreements = [...agreements];
        newAgreements[index] = !newAgreements[index];
        setAgreements(newAgreements);
      };
    const isAllAgreed = agreements.every((agreed) => agreed);
    const apply = () => {
        console.log(money)
        if (finance.productType === 'SAVINGS' && money < 1000) {
            setErrorMessage('금액이 너무 적습니다.');
            setShowDiv4(true);
            return;
        }
        if (isAllAgreed) {
          // 모든 약관에 동의한 경우에만 처리
          console.log('신청 처리');
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
          // 동의하지 않은 약관이 있는 경우
          setErrorMessage('약관에 모두 동의해야 합니다.');
          console.log('약관에 동의해야 합니다.');
        }
      };
  
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
        dispatch(getProductInfo(params.id)).then(async (product) => {
            if (product.payload.data === null)
                nav("/financial");
            await setFinance(product.payload.data);
            switch (product.payload.data === null? '' : product.payload.data.productType) {
                case 'DEPOSIT':
                    dispatch(setDepositorFinancialProductId(product.payload.data.id));
                    if (role === 'ROLE_PARENT') {
                        dispatch(getDepCustomer(product.payload.data.id))
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
                    dispatch(setSavingserFinancialProductId(product.payload.data.id));
                    if (role === 'ROLE_PARENT') {
                        dispatch(getSavCustomer(product.payload.data.id))
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
                    dispatch(setLoanerFinancialProductId(product.payload.data.id));
                    if (role === 'ROLE_PARENT') {
                        dispatch(getLoaCustomer(product.payload.data.id))
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
        });
    }
    useEffect(setBase, []);

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
        setMoney(Number(event.target.value));
        if (finance.productType === 'DEPOSIT')
            dispatch(setDepositorMoney(Number(event.target.value)));
        if (finance.productType === 'LOAN')
            dispatch(setLoanerMoney(Number(event.target.value)));
        else
            dispatch(setSavingserRegularMoney(Number(event.target.value)));
    };
    return (
        (finance.name !== undefined) && (<div className='product-detail-container'>
            <div className='product-detail-header'>
                <div className='product-detail-name'>{finance.name}</div>
                <div className='product-summary-infomation'>
                    <img className='card-product-img' src={finance.productType === 'DEPOSIT' ? Deposit : finance.productType === 'SAVINGS' ? Savings : Loan} alt="상품" />
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-rate'>                        
                        <div>{finance.rate}% </div>
                        </div>
                    </div>
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-period'>                        
                        <div>{finance.period}개월 </div></div>

                    </div>
                </div>
            </div>
            <div className='product-info-container'>
                <div className='product-space-between'>
                    <span className='option-title'>상품 안내</span>
                    <div className='option-buttons'>
                    {role === 'ROLE_PARENT' ? null :(<>
                    <label className='agreementask'>이용약관에 동의합니다.</label>
                    <input
                            type="checkbox"
                            className="agreement-checkbox" 
                            checked={agreements[0]}
                            onChange={() => toggleAgreement(0)}
                            />
                          </>)}
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
                            <div className="text-label" style={{ width: '46%' }}>{finance.period}개월</div>
                        </div>
                        <div className='flex-row'>
                            <   MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 {finance.productType === 'LOAN'? "이자":"이율"} :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{finance.rate}%</div>
                        </div>
                    </div>
                )}
                <div className='product-space-between'>
                    <span className='option-title'>가입 정보</span>
                    <div className='option-buttons'>
                    {role === 'ROLE_PARENT' ? null :(<>
                    <label className='agreementask'>이용약관에 동의합니다.</label>
                    <input
                            type="checkbox"
                            className="agreement-checkbox" 

                            checked={agreements[1]}
                            onChange={() => toggleAgreement(1)}
                            />
                            </>)}
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
                    {role === 'ROLE_PARENT' ? null :(<>
                    <label className='agreementask'>이용약관에 동의합니다.</label>
                    <input
                            type="checkbox"
                            className="agreement-checkbox" 

                            checked={agreements[2]}
                            onChange={() => toggleAgreement(2)}
                            />
                            </>)}
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
                {role === 'ROLE_PARENT' ? null : <div className='product-space-between'>
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
                    <label className='custom-label'>
                      {(finance.productType === 'DEPOSIT'? "납입 금액" : finance.productType === 'LOAN'? "대출 신청 금액":"(매월)납입 금액")}
                    </label>
                    <input
                      type="number"
                      className='finance-inputfield custom-cursor'
                      value={money}
                      onChange={handleRateChange}
                    />
                    <FaWonSign />
                  </div>
                )}
                <div className='errorMessage'>{errorMessage}</div>
            </div>

            {role === 'ROLE_PARENT' ? null : <div className='product-apply-button' onClick={apply}>상품 신청</div>}
            {role === 'ROLE_PARENT' ? <ApplyList applys={applys} type={finance.productType} /> : null}
            <div className='finance-detail-footer'>
                <Footer />
            </div>
        </div >
    )
    );
};

export default FinanceDetail;

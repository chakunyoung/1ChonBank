import React, { useEffect, useState } from 'react';
import { AiOutlineDown, AiOutlineNotification } from 'react-icons/ai';
import 'pages/account/AccountDetail.css';
import Char1 from 'assets/char1x4.png';
import Footer from 'components/common/Footer'
import { useLocation } from 'react-router-dom';
import { MdLabelOutline } from "react-icons/md";
import apis from 'services/api/apis';
import 'pages/finance/FinanceDetail.css';
import Card from 'components/banking/Card'

import Deposit from 'assets/deposit.svg';
import Savings from 'assets/savings.svg';
import Loan from 'assets/loan.svg';

const AccountDeatil = () => {
    const [showDiv1, setShowDiv1] = useState(false);
    const [showDiv2, setShowDiv2] = useState(false);
    const [showDiv3, setShowDiv3] = useState(false);

    const [product, setProudct] = useState(null);

    const location = useLocation();
    const data = location.state?.data;

    useEffect(() => {
        if (data && data.financialProductId) {
            apis.get(`/api/financial/${data.financialProductId}`)
                .then(response => {
                    console.log(response);
                    setProudct(response.data.data);
                })
                .catch(error => {
                    console.error("상품정보 가져오기 실패:", error);
                });
        }

        console.log("돈", data.money);

    }, [data]);


    return (
        <div className='accountdetail-container'>
            <div className='product-detail-header'>
                <div className='product-detail-name'>{data.productName}</div>
                <div className='product-summary-infomation'>
                    <img className='card-product-img' src={data.productType === 'DEPOSIT' ? Deposit : data.productType === 'SAVINGS' ? Savings : Loan} alt="상품" />
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-rate'>
                            <div>{product ? product.rate + "%" : "로딩 중..."}</div>
                        </div>
                    </div>
                    <div style={{ 'textAlign': 'center' }}>
                        <div className='product-summary-period'>
                            <div>{product ? product.period + "개월" : "로딩 중..."}</div>
                        </div>

                    </div>
                </div>
                <div className="accountdetail-card">
                    <Card
                        name={data.productName}
                        expiry={"2222"}
                        number={""} // data.cardNumber
                    />
                </div>

            </div>

            <div className='product-info-container'>
                <div className='product-space-between'>
                    <span className='option-title'>상품 안내</span>
                    <div className='option-buttons'>
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
                            <div className="text-label" style={{ width: '46%' }}><div>{data.productType === 'DEPOSIT' ? "예금" : data.productType === 'SAVINGS' ? "적금" : "대출"} </div></div>
                        </div>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>계약 기간 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}><div>{product ? product.period + "개월" : "로딩 중..."}</div></div>
                        </div>
                        <div className='flex-row'>
                            <   MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 { } :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{data ? data.productName : "로딩 중..."}</div>
                        </div>
                    </div>
                )}

                <div className='product-space-between'>
                    <span className='option-title'>???</span>
                    <div className='option-buttons'>
                        <span className='show-info-button' onClick={() => setShowDiv2(!showDiv2)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>
                {showDiv2 && (
                    <div className='detail-dropdown'>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 종류 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{data.userNickname}</div>
                        </div>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>계약 기간 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{ }개월</div>
                        </div>
                        <div className='flex-row'>
                            <   MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 { } :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{ }%</div>
                        </div>
                    </div>
                )}

                <div className='product-space-between'>
                    <span className='option-title'>???</span>
                    <div className='option-buttons'>
                        <span className='show-info-button' onClick={() => setShowDiv3(!showDiv3)}>
                            <AiOutlineDown />
                        </span>
                    </div>
                </div>

                {showDiv3 && (
                    <div className='detail-dropdown'>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 종류 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{data.userNickname}</div>
                        </div>
                        <div className='flex-row'>
                            <MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>계약 기간 :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{ }개월</div>
                        </div>
                        <div className='flex-row'>
                            <   MdLabelOutline className='finance-detail-icon' />
                            <div className="text-label" style={{ width: '40%' }}>상품 { } :{'    '}</div>
                            <div className="text-label" style={{ width: '46%' }}>{ }%</div>
                        </div>
                    </div>
                )}



                <div className='accountdetail-footer'>
                    <Footer />
                </div>
            </div>
        </div >
    );
};

export default AccountDeatil;

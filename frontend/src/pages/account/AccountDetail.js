import React, { useEffect, useState } from "react";
import { AiOutlineDown, AiOutlineNotification } from "react-icons/ai";
import "pages/account/AccountDetail.css";
import Char1 from "assets/char1x4.png";
import Footer from "components/common/Footer";
import { useLocation } from "react-router-dom";
import { MdLabelOutline } from "react-icons/md";
import apis from "services/api/apis";
import "pages/finance/FinanceDetail.css";
import Card from "components/banking/Card";

import Deposit from "assets/deposit.svg";
import Savings from "assets/savings.svg";
import Loan from "assets/loan.svg";

const AccountDeatil = () => {
  const [showDiv1, setShowDiv1] = useState(false);
  const [showDiv2, setShowDiv2] = useState(false);
  const [showDiv3, setShowDiv3] = useState(false);

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

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

  const location = useLocation();
  const data = location.state?.data;

  useEffect(() => {
    console.log(data);

    const fetchProduct = async () => {
      if (data && data.financialProductId) {
        try {
          const response = await apis.get(
            `/api/financial/${data.financialProductId}`
          );
          console.log(response);
          setProduct(response.data.data);
        } catch (error) {
          console.error("상품정보 가져오기 실패:", error);
        }
        setLoading(false);
      } else {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [data]);

  if (loading) {
    return null;
  }

  const dateCalc = (date) => {
    const year = date.slice(2, 4); // '24'
    const month = date.slice(4, 6); // '01'
    const formattedDate = `${year}/${month}`; // '23/10'
    return formattedDate;
  };

  return (
    <div className="accountdetail-container">
      <div className="product-detail-header">
        <div className="product-detail-name">{data.productName}</div>
        <div className="product-summary-infomation">
          <img
            className="card-product-img"
            src={
              product.productType === "DEPOSIT"
                ? Deposit
                : product.productType === "SAVINGS"
                  ? Savings
                  : Loan
            }
            alt="상품"
          />
          <div style={{ textAlign: "center" }}>
            <div className="product-summary-rate">
              <div>{product ? product.rate + "%" : "로딩 중..."}</div>
            </div>
          </div>
          <div style={{ textAlign: "center" }}>
            <div className="product-summary-period">
              <div>{product ? product.period + "개월" : "로딩 중..."}</div>
            </div>
          </div>
        </div>
        <div className="accountdetail-card">
          <Card
            name={data.regularMoney + "P"}
            expiry={dateCalc(data.expiry)}
            number={data.cardNumber} // data.cardNumber
          />
        </div>
      </div>

      <div className="product-info-container">
        <div className="product-space-between">
          <span className="option-title">상품 안내</span>
          <div className="option-buttons">
            <span
              className="show-info-button"
              onClick={() => setShowDiv1(!showDiv1)}>
              <AiOutlineDown />
            </span>
          </div>
        </div>
        {showDiv1 && (
          <div className="detail-dropdown">
            <div className="flex-row">
              <MdLabelOutline className="finance-detail-icon" />
              <div className="text-label" style={{ width: "40%" }}>
                상품 종류 :{"    "}
              </div>
              <div className="text-label" style={{ width: "46%" }}>
                <div>
                  {data.productType === "DEPOSIT"
                    ? "예금"
                    : data.productType === "SAVINGS"
                      ? "적금"
                      : "대출"}{" "}
                </div>
              </div>
            </div>
            <div className="flex-row">
              <MdLabelOutline className="finance-detail-icon" />
              <div className="text-label" style={{ width: "40%" }}>
                계약 기간 :{"    "}
              </div>
              <div className="text-label" style={{ width: "46%" }}>
                <div>{product ? product.period + "개월" : "로딩 중..."}</div>
              </div>
            </div>
            <div className="flex-row">
              <MdLabelOutline className="finance-detail-icon" />
              <div className="text-label" style={{ width: "40%" }}>
                상품 { } :{"    "}
              </div>
              <div className="text-label" style={{ width: "46%" }}>
                {data ? data.productName : "로딩 중..."}
              </div>
            </div>
          </div>
        )}

        <div className='product-space-between'>
          <span className='option-title'>{productName[product.productType]} 설명서 확인</span>
          <div className='option-buttons'>
            <span className='show-info-button' onClick={() => setShowDiv2(!showDiv2)}>
              <AiOutlineDown />
            </span>
          </div>
        </div>
        {showDiv2 && (
          <div className='text-label product-common-info'>
            {info[product.productType]}
          </div>
        )}

        <div className="accountdetail-footer">
          <Footer />
        </div>
      </div>
    </div>
  );
};

export default AccountDeatil;

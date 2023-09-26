import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Footer from "components/common/Footer";
import Profile from "components/common/Profile"
import Myaccount from "components/common/Myaccount"
import AccountDeatil from './AccountDetail';
import './Account.css';
import { useSelector } from 'react-redux';
import Card from 'components/banking/Card'
import apis from 'services/api/apis';
import { getDepositors, getLoaners, getSavings } from 'services/api/banking/bankingAPI';
import Amchart from 'components/common/Amcharts'

const Account = () => {
  const navigate = useNavigate();

  const [depositors, setDepositors] = useState([]);
  const [loaners, setLoaners] = useState([]);
  const [savings, setSavings] = useState([]);
  const [depMoney, setdepMoney] = useState(0);
  const [loaMoney, setLoaMoney] = useState(0);
  const [savMoney, setSavMoney] = useState(0);

  const user = useSelector((state) => state.auth.user); // Redux store에서 user 정보를 가져옵니다.

  useEffect(() => {
    const fetchData = async () => {
      const fetchedDepositors = await getDepositors();
      const fetchedLoaners = await getLoaners();
      const fetchedSavings = await getSavings();

      setDepositors(fetchedDepositors.data);
      setLoaners(fetchedLoaners.data);
      setSavings(fetchedSavings.data);
      let dep = 0;
      for (let index = 0; index < fetchedDepositors.data.length; index++) {
        dep += fetchedDepositors.data[index].money;
      }
      setLoaMoney(dep);
      let loa = 0;
      for (let index = 0; index < fetchedLoaners.data.length; index++) {
        loa += fetchedLoaners.data[index].money;
      }
      setLoaMoney(loa);
      let sav = 0;
      for (let index = 0; index < fetchedSavings.data.length; index++) {
        sav += fetchedSavings.data[index].money;
      }
      setLoaMoney(sav);
      console.log(fetchedDepositors.data);
      console.log(fetchedLoaners.data);
      console.log(fetchedSavings.data);

    };
    fetchData();
  }, []);

  const goToDetail = () => {
    navigate("/accountDetail");
  };

  return (
    <div className='account-container'>
      <div className='account-profilecontainer'>
        <Profile />
      </div>
      <Myaccount />
      <ul>
        <li>보유 현금 : {user.money}</li>
        <li>총 자산 : {(user.money + depMoney + savMoney - loaMoney)}</li>
        <li>예금 자산 : {depMoney}</li>
        <li>적금 자산 : {savMoney}</li>
        <li>대출 자산 : {loaMoney}</li>
      </ul>
      <div>
      </div>
      {depositors.length ? (
        depositors.map((depositor, index) => (
          <div className='card-margin' onClick={goToDetail}>
            <Card
              key={index}
              name={depositor.productName}
              expiry={"2222"}
              number={depositor.cardNumber}
            />
          </div>

        ))
      ) : (
        <p>등록한 예금이 없습니다.</p>
      )}

      {loaners.length ? (
        loaners.map((loaner, index) => (
          <div className='card-margin' onClick={goToDetail}>
            <Card
              key={index}
              name={loaner.productName}
              expiry={"2222"}
              number={loaner.cardNumber}
            />
          </div>
        ))
      ) : (
        <p>등록한 대출이 없습니다.</p>
      )}


      {savings.length ? (
        savings.map((saving, index) => (
          <div className='card-margin' onClick={goToDetail}>
            <Card
              key={index}
              name={saving.productName
              }
              expiry={"2222"}
              number={saving.cardNumber}
            />
          </div>
        ))
      ) : (
        <p>등록한 적금이 없습니다.</p>
      )}

      <div className='account-footer'>
        <Footer />
      </div>
      <Amchart savings={user.money} depMoney={depMoney} loaMoney={loaMoney} savMoney={savMoney} />
    </div>
  );
};

export default Account;


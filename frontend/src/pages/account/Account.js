import React, { useEffect, useState } from 'react';
import Footer from "components/common/Footer";
import Profile from "components/common/Profile"
import Myaccount from "components/common/Myaccount"
import './Account.css';
import { useSelector } from 'react-redux';
import { getDepositors, getLoaners, getSavings } from 'services/api/banking/bankingAPI';
import { getFamilyMembers } from 'redux/Family';
import Amchart from 'components/common/Amcharts';
import ProductSet from 'components/finance/ProductSet';
import ParentAccountPage from './ParentAccountPage';

const Account = () => {
  const [depositors, setDepositors] = useState([]);
  const [loaners, setLoaners] = useState([]);
  const [savings, setSavings] = useState([]);
  const [depMoney, setdepMoney] = useState(0);
  const [loaMoney, setLoaMoney] = useState(0);
  const [savMoney, setSavMoney] = useState(0);
  const [childIdx, setChildIdx] = useState(0);
  const [childs, setChilds] = useState([]);
  const user = useSelector((state) => state.auth.user); // Redux store에서 user 정보를 가져옵니다.
  const handlerLeftClick = () =>{
    setChildIdx((childIdx + childs.length - 1) % childs.length);
  }
  const handlerRightClick = () =>{
    setChildIdx((childIdx + 1) % childs.length);
  }
  useEffect(() => {
    const fetchChildData = async () => {
      const familys = await getFamilyMembers();
      let temp = [];
      for (let i = 0; i < familys.length; i++) {
        if (familys[i].role === "ROLE_CHILD")
          temp.push(familys[i]);
      }
      setChilds(temp);
    };
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
      setdepMoney(dep);
      let loa = 0;
      for (let index = 0; index < fetchedLoaners.data.length; index++) {
        loa += fetchedLoaners.data[index].money;
      }
      setLoaMoney(loa);
      let sav = 0;
      for (let index = 0; index < fetchedSavings.data.length; index++) {
        sav += fetchedSavings.data[index].money;
      }
      setSavMoney(sav);
    };
    if (user.roles == 'ROLE_PARENT')
      fetchChildData();
    else
      fetchData();
  }, []);

  let componentToRender = null;

  if (user.roles === 'ROLE_PARENT') {
    if (childs.length !== 0) {
      componentToRender = <ParentAccountPage child={childs[childIdx]} handlerLeftClick={handlerLeftClick} handlerRightClick={handlerRightClick} />;
    }
    // 'childs.length === 0' 경우에는 componentToRender가 null로 유지되므로 아무 것도 렌더링되지 않습니다.
  } else {
    componentToRender = <ProductSet depositors={depositors} loaners={loaners} savings={savings} />;
  }

  return (
    <div className='account-container'>
      <div className='account-profilecontainer'>
        <Profile />
      </div>
      <Myaccount />
      {user.roles === 'ROLE_PARENT' ? null : <Amchart savings={user.money} depMoney={depMoney} loaMoney={loaMoney} savMoney={savMoney} />}
      {componentToRender}
      <div className='account-footer'>
        <Footer />
      </div>
    </div>
  );
};

export default Account;


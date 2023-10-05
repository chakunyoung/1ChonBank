import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { getDepositorByNickname, getLoanerByNickname, getSavingserByNickname } from 'services/api/customer/customerAPI';
import ProductSet from 'components/finance/ProductSet';
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import './ParentAccountPage.css';
import Amchart from 'components/common/Amcharts'
import Myaccount from 'components/common/Myaccount';

const ParentAccountPage = (props) => {
    const [depositors, setDepositors] = useState([]);
    const [loaners, setLoaners] = useState([]);
    const [savings, setSavings] = useState([]);
    const user = useSelector((state) => state.auth.user); // Redux store에서 user 정보를 가져옵니다.
    const [depMoney, setdepMoney] = useState(0);
    const [loaMoney, setLoaMoney] = useState(0);
    const [savMoney, setSavMoney] = useState(0);
    useEffect(() => {
        const fetchData = async (res) => {
            const fetchedDepositors = await getDepositorByNickname(res.nickname);
            const fetchedSavings = await getSavingserByNickname(res.nickname);
            const fetchedLoaners = await getLoanerByNickname(res.nickname);

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
        fetchData(props.child);
    }, [props.child]);
    const leftClick = () => {
        props.handlerLeftClick();
    }
    const rightClick = () => {
        props.handlerRightClick();
    }
    return (
        <div>
            <div className='accountChildSelect'>
                <div className='leftchildSelectButton' onClick={leftClick}><IoIosArrowDropleft /></div>
                <div>{props.child.nickname}</div>
                <div className='rightchildSelectButton' onClick={rightClick}><IoIosArrowDropright /></div>
            </div>
            <Amchart savings={props.child.money} depMoney={depMoney} loaMoney={loaMoney} savMoney={savMoney} />
            <ProductSet depositors={depositors} loaners={loaners} savings={savings} />
        </div>
    );
};
export default ParentAccountPage;

import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { getDepositorByNickname, getLoanerByNickname, getSavingserByNickname } from 'services/api/customer/customerAPI';
import ProductSet from 'components/finance/ProductSet';
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import './ParentAccountPage.css';

const ParentAccountPage = (props) => {
    const [depositors, setDepositors] = useState([]);
    const [loaners, setLoaners] = useState([]);
    const [savings, setSavings] = useState([]);
    const user = useSelector((state) => state.auth.user); // Redux store에서 user 정보를 가져옵니다.
    useEffect(() => {
        const fetchData = async (res) => {
            console.log(res);
            console.log(res.nickname);
            const fetchedDepositors = await getDepositorByNickname(res.nickname);
            const fetchedLoaners = await getSavingserByNickname(res.nickname);
            const fetchedSavings = await getLoanerByNickname(res.nickname);

            setDepositors(fetchedDepositors.data);
            setLoaners(fetchedLoaners.data);
            setSavings(fetchedSavings.data);
            console.log(depositors);
            console.log(loaners);
            console.log(savings);
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
            <ProductSet depositors={depositors} loaners={loaners} savings={savings} />
        </div>
    );
};
export default ParentAccountPage;

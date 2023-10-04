import React, { useEffect, useState } from 'react';
import './ApplyCard.css';
const ApplyCard = (props) => {
    console.log(props.proType);
    const [memo, setMemo] = useState('');
    const setMemoHandler = (proType) => {
        switch (proType) {
            case "DEPOSIT":
                setMemo("총 납입 금액 : " + props.applys.money + "P");
                break;
            case "SAVINGS":
                setMemo("매월 납입 금액 : " + props.applys.regularMoney + "P");
                break;
            case "LOAN":
                setMemo("대출 신청 금액 : " + props.applys.money + "P");
                break;
            default:
                break;
        }
    }
    useEffect(() => {
        setMemoHandler(props.proType);
    }, [props.proType]);
    const applyHandler = () => {
        props.applyItem(props.applys.id);
    };
    const refuseHandler = () => {
        props.refuseItem(props.applys.id);
    };
    return (
        <div>
            {props.applys.grant === null ?
                <div className='applyRealCard'>
                    <div className='applyMenuSet'>
                        <h3>{props.applys.userNickname}</h3>
                        <div className='applyCardMenu'>
                            <button className='apply-allowbutton' onClick={applyHandler}>수락</button>
                            <button className='apply-deletebutton' onClick={refuseHandler}>거절</button>
                        </div>
                    </div>
                    <div className='applyCardInfo'>
                        {memo}
                    </div>
                </div> : null}
        </div>
    );
};
export default ApplyCard;
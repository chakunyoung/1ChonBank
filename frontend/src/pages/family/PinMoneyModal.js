import React, { useState } from 'react';
import './PinMoneyModal.css';
import './myfamily.css';
import apis from 'services/api/apis';
import { useDispatch, useSelector } from 'react-redux';
import { DepositMoney } from "redux/Auth";
const PinMoneyModal = ({ show, onClose, child, children, onDataChange }) => {
    const [inputValue, setInputValue] = useState('');
    const currentDate = new Date();
    const currentDateString = currentDate.toISOString().split('T')[0];
    const user = useSelector((state) => state.auth.user);
    const dispatch = useDispatch();
    const pinmoneyAssign = async  () => {
        try {
            await apis.post("api/banking/pinmoney", {
                childNickname: child.nickname,
                pinMoney: inputValue,
                receiveTime: currentDateString,
            });
            onDataChange(inputValue);
            dispatch(DepositMoney(inputValue));
            onClose();
        } catch (error) {
            console.error('용돈 지정에 실패했습니다:', error);
        }
    }

    if (!show) return null;

    return (
        <>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="modal-content">
                {children}
                <div className="input-group">
                    <label htmlFor="pin-money">아이 이름: {child.nickname}</label>
                    <input
                        type="text"
                        id="pin-money"
                        value={inputValue}
                        onChange={e => setInputValue(e.target.value)}
                        placeholder="매주 용돈 액수를 지정해주세요."
                    />
                </div>
                <div className="familymenu"><button className="family-missionbutton" onClick={pinmoneyAssign}>지정</button><button className="family-missionbutton" onClick={onClose}>닫기</button></div>
            </div >
        </>
    );
};

export default PinMoneyModal;

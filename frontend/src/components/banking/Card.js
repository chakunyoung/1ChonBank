import React, { useState } from 'react';
import Cards from 'react-credit-cards-2';
import 'react-credit-cards-2/dist/es/styles-compiled.css';
import './custom-card.scss'; // SCSS 파일 임포트

const PaymentForm = ({ name, expiry, number }) => {
    const [state, setState] = useState({
      number: number || '9999999999999999',
      expiry: expiry || '09/23',
      cvc: '',
      name: name || '기본 값',
      focus: '',
    });
  
    const handleInputChange = (evt) => {
      const { name, value } = evt.target;
      setState((prev) => ({ ...prev, [name]: value }));
    };
  
    const handleInputFocus = (evt) => {
      setState((prev) => ({ ...prev, focus: evt.target.name }));
    };
  
    return (
      <div>
        <Cards
          number={state.number}
          expiry={state.expiry}
          cvc={state.cvc}
          name={state.name}
          focused={state.focus}
        />
        {/* 입력 필드를 제거했다고 가정하면, 다른 부분은 그대로 둡니다. */}
      </div>
    );
  };
  
  export default PaymentForm;
  
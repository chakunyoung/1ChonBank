import React, { useState } from 'react';
import { AiOutlineDown, AiOutlineNotification } from 'react-icons/ai';
import 'pages/account/AccountDetail.css';
import Char1 from 'assets/char1x4.png';
import Footer from 'components/common/Footer'

const AccountDeatil = () => {
    const [showDiv1, setShowDiv1] = useState(false);
    const [showDiv2, setShowDiv2] = useState(false);
    const [showDiv3, setShowDiv3] = useState(false);

    return (
        <div className='accountdetail-container'>
            <div className='deposit-withdrawal-button-div'>
                <div className='btn deposit-btn'>
                    <span className='btn-text'>입금</span>
                </div>
                <div className='btn withdrawal-btn'>
                    <span className='btn-text'>출금</span>
                </div>
            </div>

            <div className='image-container'>
                <img src={Char1} alt='캐릭터' />
            </div>

            <div className='flex-space-between'>
                <span className='margin-left'>상품 안내</span>
                <button style={{ marginRight: '0.5rem', marginLeft: 'auto' }} className='margin-right' onClick={() => setShowDiv1(!showDiv1)}>
                    <AiOutlineDown />
                </button>
            </div>

            {showDiv1 && (
                <div className='dropdown'>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 1</div>
                    </div>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 2</div>
                    </div>
                </div>
            )}

            <div className='flex-space-between'>
                <span className='margin-left'>금리, 수수료 정보</span>
                <button style={{ marginRight: '0.5rem', marginLeft: 'auto' }} className='margin-right' onClick={() => setShowDiv2(!showDiv2)}>
                    <AiOutlineDown />
                </button>
            </div>

            {showDiv2 && (
                <div className='dropdown'>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 1</div>
                    </div>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 2</div>
                    </div>
                </div>
            )}

            <div className='flex-space-between'>
                <span className='margin-left'>거래 내역</span>
                <button style={{ marginRight: '0.5rem', marginLeft: 'auto' }} className='margin-right' onClick={() => setShowDiv3(!showDiv3)}>
                    <AiOutlineDown />
                </button>
            </div>

            {showDiv3 && (
                <div className='dropdown'>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 1</div>
                    </div>
                    <div className='flex-row'>
                        <AiOutlineNotification className="icon" />
                        <div className="text-label">Section Header Text 2</div>
                    </div>
                </div>
            )}
            <Footer/>

        </div>
    );
};

export default AccountDeatil;

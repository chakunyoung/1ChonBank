import React from 'react';

const ApplyCard = (props) => {

    const applyHandler = () => {
        console.log(1);
        props.applyItem(props.applys.id);
    };
    const refuseHandler = () => {
        props.refuseItem(props.applys.id);
    };
    return (
        <div>
            {props.applys.grant === null ?
                <div>
                    <h3>상품이름 : {props.applys.productName}</h3>
                    <div>유저 정보들어갈 곳 dto 수정 필요</div>
                    <div>
                        <button onClick={applyHandler}>수락</button>
                        <button onClick={refuseHandler}>거절</button>
                    </div>
                </div> : null}
        </div>
    );
};
export default ApplyCard;
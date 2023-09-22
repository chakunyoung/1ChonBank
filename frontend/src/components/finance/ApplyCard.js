import React from 'react';

const ApplyCard = (props) => {

    const applyHandler = () => {        
        props.applyItem(props.applys.id);
    };
    const refuseHandler = () => {
        props.refuseItem(props.applys.id);
    };
    return (
        <div>
            {props.applys.grant === null ?
                <div>
                    <h3>유저 이름 : {props.applys.userNickname}</h3>
                    <div>
                        <button onClick={applyHandler}>수락</button>
                        <button onClick={refuseHandler}>거절</button>
                    </div>
                </div> : null}
        </div>
    );
};
export default ApplyCard;
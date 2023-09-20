import React, { useEffect, useState } from 'react';
import ApplyCard from './ApplyCard';
import { allowDepositor, refuseDepositor } from "redux/Depositor";
import { allowSavingser, refuseSavingser } from "redux/Savingser";
import { allowLoaner, refuseLoaner } from "redux/Loaner";
import { useDispatch } from 'react-redux';

const ApplyList = (props) => {
    const [applys, setApplays] = useState([]);
    const dispatch = useDispatch();
    const setBase = () => {
        setApplays(props.applys);
    }
    useEffect(setBase, []);
    const applyItem = (num) => {
        switch (props.type) {
            case "DEPOSIT":
                applyD(num);
                break;
            case "SAVINGS":
                applyS(num);
                break;
            case "LOAN":
                applyL(num);
                break;
            default:
                break;
        }
    };
    const refuseItem = (num) => {
        switch (props.type) {
            case "DEPOSIT":
                refuseD(num);
                break;
            case "SAVINGS":
                refuseS(num);
                break;
            case "LOAN":
                refuseL(num);
                break;
            default:
                break;
        }
    };
    const applyD = (num) => {
        dispatch(allowDepositor(num))
            .then((resultAction) => {
                if (allowDepositor.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (allowDepositor.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const applyS = (num) => {
        dispatch(allowSavingser(num))
            .then((resultAction) => {
                if (allowSavingser.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (allowSavingser.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const applyL = (num) => {
        dispatch(allowLoaner(num))
            .then((resultAction) => {
                if (allowLoaner.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (allowLoaner.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const refuseD = (num) => {
        dispatch(refuseDepositor(num))
            .then((resultAction) => {
                if (refuseDepositor.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (refuseDepositor.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const refuseS = (num) => {
        dispatch(refuseSavingser(num))
            .then((resultAction) => {
                if (refuseSavingser.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (refuseSavingser.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const refuseL = (num) => {
        dispatch(refuseLoaner(num))
            .then((resultAction) => {
                if (refuseLoaner.fulfilled.match(resultAction)) {
                    // 성공적으로 완료됐을 때
                    const success = resultAction.payload; // 액션의 payload에 결과 데이터가 있을 것입니다.
                    const updatedApplys = applys.filter(item => item.id !== num);
                    setApplays(updatedApplys);
                } else if (refuseLoaner.rejected.match(resultAction)) {
                    // 작업이 실패했을 때
                    const error = resultAction.payload; // 액션의 payload에 오류 데이터가 있을 것입니다.
                    console.log(error);
                }
            });
    }
    const rendering = () => {
        const result = [];
        for (let i = 0; i < applys.length; i++) {
            const p = applys[i];
            result.push(<div className='card-apply-box' key={applys[i].id}><ApplyCard applyItem={applyItem} refuseItem={refuseItem} applys={p}></ApplyCard></div>);
        }
        return result;
    };
    return (
        <div className='apply-product-container'>
            {rendering()}
        </div>
    );
};
export default ApplyList;


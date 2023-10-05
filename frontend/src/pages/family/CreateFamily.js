import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setFamilyName } from "redux/Family";
import { setFamilyId } from "redux/Finance";
import { AiOutlineUsergroupAdd } from "react-icons/ai";

import apis from "services/api/apis";
import CryIcon from "assets/cry.gif"
import Footer from "components/common/Footer";
import "./CreateFamily.css";
import './myfamily.css';
import './PinMoneyModal.css';

function CreateFamily() {
  const [inputFamilyName, setInputFamilyName] = useState('');
  const [showInput, setShowInput] = useState(false); // 가족명 입력 요소의 가시성 상태 추가
  const familyName = useSelector((state) => state.family.familyName);
  const role = useSelector((state) => state.auth.user.roles); // 가족 구성원의 역할 정보를 가져옵니다.
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isFamilyNameExist = useSelector((state) => state.auth.user.familyName);
  const [isInvitationExist, setIsInvitationExist] = useState(null);
  const userNickname = useSelector((state) => state.auth.user.userNickname);
  const [familyNickname, setFamilyNickname] = useState(null);

  const handleCreateFamily = () => {
    if (inputFamilyName === '') {
      alert("한 글자 이상 입력하세요.");
      return;
    }

    dispatch(setFamilyName(inputFamilyName));
    // familyName 변수를 이용하여 경로를 구성하여 POST 요청을 보냅니다.
    apis
      .post(`/api/families/${inputFamilyName}`)
      .then((response) => {
        // 성공적인 응답 처리
        alert("가족 생성을 축하드립니다!");
        dispatch(setFamilyId(response.data.data));
        console.log(response.data.data);
        navigate("/");
      })
      .catch((error) => {
        // 오류 처리
        console.error(error);
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apis.get('/api/families/invitationUser')
        console.log(response);
        const invitationData = response.data.data;
        console.log(invitationData.familyNickname + "인비테이션 데이터");
        setIsInvitationExist(invitationData);

      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);


  const handleSetFamilyName = (e) => {
    setInputFamilyName(e.target.value);
    console.log(inputFamilyName);
  };

  const handleAccept = async () => {
    try {
      const familyInvitationDto = {
        familyNickname: isInvitationExist.familyNickname,
      };
      console.log(familyInvitationDto);

      const response = await apis.put('/api/families/acceptInvitation', familyInvitationDto);

      console.log(response);

      if (response) {
        alert("가족 초대를 수락했습니다!");
        navigate("/");
      } else {
        console.error('요청 실패:', response.statusText);
      }
    } catch (error) {
      console.error('오류 발생:', error);
    }
  };



  const handleReject = async () => {
    try {
      const familyNickname = isInvitationExist.familyNickname;

      const response = await apis.delete(`/api/families/deleteInvitation?familyNickname=${familyNickname}`);

      if (response.status === 200) {
        alert("가족 거절완료");
        navigate("/");
      } else {
        console.error('요청 실패:', response.statusText);
      }
    } catch (error) {
      console.error('오류 발생:', error);
    }
  };


  const toggleInput = () => {
    setShowInput(!showInput);
  };

  return (
    <div className="CreateFamilyContainer">
      {role === 'ROLE_CHILD' ? (
        isFamilyNameExist === null && isInvitationExist === null ? (
          <h1 style={{
            color: '#9370DB',
            marginTop: '50px',
            marginBottom: '200px',
          }}>
            아직 가족이 없습니다
            <br /><br />
            {/* <img
              src={CryIcon}
              alt="Cry Icon"
              className="Cry-icon"
            /> */}
          </h1>
        ) : (
          <div className="familymenu">
            <span style={{ fontSize: '17px', fontWeight: 'bold' }}>
              {isInvitationExist.familyNickname} 가족으로 부터 초대 메시지가 왔습니다.
            </span>
            <br></br>
            <button className="pinmoney-button" onClick={() => handleAccept()}>수락</button>
            <button className="pinmoney-button" onClick={() => handleReject()}>거절</button>
          </div>
        )
      ) : (
        <>
          {showInput ? (
            <div>
              <div className='familyname-maketext'>가족 만들기</div>
              <input
                className='familyname-input'
                type="text"
                placeholder="가족 이름을 입력하세요."
                value={inputFamilyName}
                onChange={handleSetFamilyName}
              />
              <button className='makefamily-button' onClick={handleCreateFamily}>만들기</button>
            </div>
          ) : (
            <div className='plusicon-container'>
              <AiOutlineUsergroupAdd
                className="PlusIcon"
                onClick={toggleInput} // Plus 아이콘을 클릭하면 가족명 입력 요소를 표시
              />
              가족이 없습니다. 버튼을 눌러 가족을 만들어보세요.
            </div>
          )}
        </>
      )}
    </div>
  );

}

export default CreateFamily;

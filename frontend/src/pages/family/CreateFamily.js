import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setFamilyName } from "redux/Family";
import { setFamilyId } from "redux/Finance";
import apis from "services/api/apis";
import CryIcon from "assets/cry.gif"
import Footer from "components/common/Footer";
import "./CreateFamily.css";

function CreateFamily() {
  const [inputFamilyName, setInputFamilyName] = useState('');
  const familyName = useSelector((state) => state.family.familyName);
  const role = useSelector((state) => state.auth.user.roles); // 가족 구성원의 역할 정보를 가져옵니다.
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isFamilyNameExist = useSelector((state) => state.auth.user.familyName);
  const [isInvitationExist, setIsInvitationExist] = useState(null);
  const userNickname = useSelector((state) => state.auth.user.userNickname);

  const handleCreateFamily = () => {
    if (inputFamilyName === '') {
      alert("한글자 이상 입력하세요.");
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
        console.log(invitationData.familyNickname+"인비테이션 데이터");
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
      } else {
        console.error('요청 실패:', response.statusText);
      }
    } catch (error) {
      console.error('오류 발생:', error);
    }
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
            <img
              src={CryIcon}
              alt="Cry Icon"
              className="Cry-icon"
            />
          </h1>
          
        ) : (
          <div>
            <div onClick={() => handleAccept()}>수락</div>
            <div onClick={() => handleReject()}>거절</div>
          </div>
        )
      ) : (
        <>
          <input
            type="text"
            placeholder="가족명 입력란"
            value={inputFamilyName}
            onChange={handleSetFamilyName}
          />
          <button onClick={handleCreateFamily}>생성</button>
        </>
      )}
    </div>
  );
  
}

export default CreateFamily;

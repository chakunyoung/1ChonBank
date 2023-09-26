import React, { useState } from 'react';
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

  const handleCreateFamily = () => {
    if (inputFamilyName === '') {
      alert("한글자 이상 입력하세요.");
      return;
    }

    dispatch(setFamilyName(inputFamilyName));
    // familyName 변수를 이용하여 경로를 구성하여 POST 요청을 보냅니다.
    apis
      .post(`/api/families/${familyName}`)
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

  const handleSetFamilyName = (e) => {
    setInputFamilyName(e.target.value);
    console.log(inputFamilyName);
  };

  return (
    <div className="CreateFamilyContainer">
      {role === 'ROLE_CHILD' ? (

        <h1 style={{
          // 텍스트를 가운데 정렬
          // 텍스트를 가운데 정렬

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

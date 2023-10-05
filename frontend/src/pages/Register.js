import React, { useEffect, useState } from "react";
import "./Register.css";
import char2 from "assets/char2x4.png";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import apis from "services/api/apis";
import { setUser } from "redux/Auth";
import CharacterCard from "components/common/CharacterCard";

const Register = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [nicknameError, setNicknameError] = useState(""); // State for nickname error message
  const user = useSelector((state) => state.auth.user);

  useEffect(() => {
    if (user.nickname != null) {
      navigate("/mypage");
    }
  }, []);

  const handleCheckboxChange = (selectedRole) => {
    const updatedUser = { ...user, roles: selectedRole };

    // updateUser 액션을 디스패치하여 상태를 업데이트합니다.
    dispatch(setUser(updatedUser));
  };

  const handleCardClick = (selectedRole) => {
    if (selectedRole !== user.roles) {
      const updatedUser = { ...user, roles: selectedRole };

      // updateUser 액션을 디스패치하여 상태를 업데이트합니다.
      dispatch(setUser(updatedUser));
    }
  };

  const handleNicknameChange = (event) => {
    const newNickname = event.target.value;

    if (newNickname.length > 10) {
      setNicknameError("닉네임의 최대 길이는 10자입니다.");
    } else {
      const updatedUser = { ...user, nickname: newNickname };
      dispatch(setUser(updatedUser));
      setNicknameError(""); // Clear the error message if the nickname is within the limit
    }
  };

  const handleSaveUser = async () => {
    try {
      if (user.nickname.length === 0) {
        alert("닉네임을 한글자 이상 입력하시오");
        return;
      }
      const response = await apis.get(`/api/user/duplication/${user.nickname}`);
      const { success, status } = response;

      if (status === 200 || success === "true") {
        // 닉네임이 중복되지 않는 경우

        // 회원가입 성공한 경우
        // console.log(user);
        apis.post("/api/user/saveUser", user);
        alert("회원가입 성공");
        navigate("/mypage");
      } else {
        // 닉네임이 중복된 경우 또는 서버에서 다른 오류가 발생한 경우
        alert("닉네임 중복"); // 서버에서 반환된 메시지를 표시
      }
    } catch (error) {
      // 중복 체크 요청 실패한 경우에 대한 처리를 여기에 추가할 수 있습니다.
      alert("중복된 닉네임 입니다.");
    }
  };

  return (
    <div className="register-container">
      <div className="register-title">회원가입</div>
      <CharacterCard />
      <div
        className="register-card"
        onClick={() => handleCardClick("ROLE_PARENT")}>
        <label className="card-label">부모</label>
        <input
          type="checkbox"
          className="register-checkbox"
          checked={user.roles === "ROLE_PARENT"}
          onChange={() => handleCheckboxChange("ROLE_PARENT")}
        />
      </div>
      <div
        className="register-card"
        onClick={() => handleCardClick("ROLE_CHILD")}>
        <label className="card-label">자녀</label>
        <input
          type="checkbox"
          className="register-checkbox"
          checked={user.roles === "ROLE_CHILD"}
          onChange={() => handleCheckboxChange("ROLE_CHILD")}
        />
      </div>
      <div className="register-card">
        {" "}
        {/* 텍스트 입력 필드 카드 */}
        <label className="card-label">닉네임</label>
        <input
          type="text"
          value={user.nickname || ""}
          onChange={handleNicknameChange}
          className="custom-input"
          maxLength="10" // Use "maxLength" attribute to limit the input length
        />
      </div>
      {nicknameError && <p className="nickname-error">{nicknameError}</p>}{" "}
      {/* Display error message */}
      <div className="register-button-container">
        <button className="register-button" onClick={handleSaveUser}>
          가입
        </button>

        <div className="register-background-box"></div>
      </div>
    </div>
  );
};

export default Register;

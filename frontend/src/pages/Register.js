import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Register.css';
import char2 from 'assets/char2x4.png';

const Register = () => {
  const [role, setRole] = useState(null);
  const [nickname, setNickname] = useState("");
  const [nicknameError, setNicknameError] = useState(""); // State for nickname error message

  const handleCheckboxChange = (selectedRole) => {
    setRole(selectedRole);
  };

  const handleCardClick = (selectedRole) => {
    if (selectedRole === role) {
      setRole(null);
    } else {
      setRole(selectedRole);
    }
  };

  const handleNicknameChange = (event) => {
    const newNickname = event.target.value;
    setNickname(newNickname);

    if (newNickname.length > 10) {
      setNicknameError("닉네임의 최대 길이는 10자입니다.");
    } else {
      setNicknameError(""); // Clear the error message if the nickname is within the limit
    }
  };

  return (
    <div className='register-container'>
      <div className='char2-container'>
        <div className='char2-text'>
          <p>회원가입</p>
        </div>
        <img src={char2} alt="char2" style={{ width: '200px' }} />
      </div>

      <div className="card" onClick={() => handleCardClick("parents")}>
        <label className="card-label">
          부모
        </label>
        <input
          type="checkbox"
          className="custom-checkbox"
          checked={role === "parents"}
          onChange={() => handleCheckboxChange("parents")}
        />
      </div>

      <div className="card" onClick={() => handleCardClick("children")}>
        <label className="card-label">
          자녀
        </label>
        <input
          type="checkbox"
          checked={role === "children"}
          onChange={() => handleCheckboxChange("children")}
        />
      </div>

      <div className="card"> {/* 텍스트 입력 필드 카드 */}
        <label className="card-label">
          닉네임
          </label>
        <input
          type="text"
          value={nickname}
          onChange={handleNicknameChange}
          className="custom-input"
          maxLength="10" // Use "maxLength" attribute to limit the input length
        />
      </div>
      {nicknameError && <p className="nickname-error">{nicknameError}</p>} {/* Display error message */}
      <div className="role-button-container">
        <Link to="/mypage">
          <button className="role-button">
            가입
          </button>
        </Link>
        <div className="background-box"></div>
      </div>
    </div>
  );
};

export default Register;

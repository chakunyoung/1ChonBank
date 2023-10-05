import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setFamilyName } from "redux/Family";
import apis from "services/api/apis";
import { MdModeEdit } from "react-icons/md";
import { IoTrash } from "react-icons/io5";
import { BsFillTrash3Fill } from "react-icons/bs";

import "./familynamesetting.css";

function ChangeFamilyName() {
  const role = useSelector((state) => state.auth.user.roles);
  const familyName = useSelector((state) => state.family.familyName);
  // console.log(familyName);

  const [editing, setEditing] = useState(false);
  const [newFamilyName, setNewFamilyName] = useState(familyName);
  const dispatch = useDispatch();

  const handleModifyFamilyName = () => {
    setEditing(true);
  };

  const handleSaveFamilyName = () => {
    // 여기에서 API를 호출하여 서버에 새로운 이름을 업데이트할 수 있습니다.
    // 예를 들어, onUpdateFamilyName 함수를 호출하여 업데이트할 수 있습니다.
    dispatch(setFamilyName(newFamilyName));

    apis.patch(`/api/families/${newFamilyName}`).then((response) => {
      //   console.log(response.data);
    });

    // 수정 모드를 종료합니다.
    setEditing(false);
  };

  const handleCancelEdit = () => {
    // 수정 모드를 종료하고 이전 이름으로 돌아갑니다.
    setNewFamilyName(familyName);
    setEditing(false);
  };

  return (
    <div>
      {editing ? (
        <div>
          <input
            type="text"
            value={newFamilyName}
            onChange={(e) => setNewFamilyName(e.target.value)}
          />
          <button className="edit-button" onClick={handleSaveFamilyName}>
            수정
          </button>
          <button className="no-button" onClick={handleCancelEdit}>
            취소
          </button>
        </div>
      ) : (
        <div className="family-name-setting-container">
          <span>{familyName}&nbsp;</span>
          {role === "ROLE_PARENT" ? (
            <button
              onClick={handleModifyFamilyName}
              className="family-setting-button">
              <MdModeEdit />
            </button>
          ) : (
            <div>{""}</div>
          )}
        </div>
      )}
    </div>
  );
}

export default ChangeFamilyName;

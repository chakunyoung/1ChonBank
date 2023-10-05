import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { setDeleteFamily, setFamilyMember } from "redux/Family";
import apis from "services/api/apis";
import ChangeFamilyName from "./ChangeFamilyName";
import FamilyList from "./FamilyList";
import Profile from "components/common/Profile";
import Footer from "components/common/Footer";
import { MdGroupAdd, MdGroupOff } from "react-icons/md";
import "./myfamily.css";
import Wrapper from "components/common/Wrapper";
import { motion } from "framer-motion";

function MyFamily() {
  const role = useSelector((state) => state.auth.user.roles);
  const familyName = useSelector((state) => state.family.familyName);
  const familyMember = useSelector((state) => state.family.familyMember);
  // const familyMemberLen = familyMember.length;
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleDeleteFamily = () => {
    if (role === "ROLE_CHILD") {
      alert("넌 안돼!임마!");
      return;
    }
    if (familyMember.length !== 1) {
      alert("자식이 남아있어 삭제가 불가 합니다.");
      return;
    }
    const confirmed = window.confirm("정말 삭제하시겠습니까?");
    if (confirmed) {
      dispatch(setDeleteFamily());
      apis.delete(`/api/families`).then((response) => {
        alert("삭제되었습니다.");
        navigate("/");
      });
    }
  };
  useEffect(() => {
    // 데이터를 가져오는 비동기 함수를 정의합니다.
    const fetchData = async () => {
      try {
        const response = await apis.get("/api/families");
        const data = response.data.data;
        dispatch(setFamilyMember(data));
        // 데이터 로딩이 완료됨을 표시
      } catch (error) {
        console.error("데이터를 가져오지 못했습니다:", error);
        // 데이터 로딩 실패를 표시
      }
    };

    fetchData(); // 데이터 가져오기 함수 호출
  }, []);

  const [isModalOpen, setModalOpen] = useState(false);
  const [searchNickname, setSearchNickname] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [selectedNickname, setSelectedNickname] = useState(""); // 선택한 닉네임 상태 추가
  const [familyData, setFamilyData] = useState([]);
  const [isChildrenExist, setIsChildrenExist] = useState(0);

  const handleSelectFamily = (nickname) => {
    setSelectedNickname(nickname); // 선택한 닉네임 설정
  };

  const handleAddFamilyMember = () => {
    setModalOpen(true);
  };

  const handleRequest = () => {
    apis
      .post(`/api/families/invitation/${selectedNickname}`)
      .then((response) => {});
    // 선택한 닉네임을 이용하여 요청을 보내는 로직을 추가하세요.
    // console.log('선택한 닉네임을 이용하여 요청을 보냅니다:', selectedNickname);
    setModalOpen(false); // "요청보내기" 버튼을 눌렀을 때 모달을 닫습니다.
  };

  const searchFamily = () => {
    apis
      .get(`/api/user/findFamily/${searchNickname}`)

      .then((response) => {
        if (Array.isArray(response.data.data)) {
          setSearchResults(response.data.data);
        } else {
          console.error("API 응답이 배열 형식이 아닙니다.");
        }
      })
      .catch((error) => {
        console.error("검색 오류:", error);
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apis.get("/api/families");
        const data = response.data.data;
        dispatch(setFamilyMember(data));
      } catch (error) {
        console.error("데이터를 가져오지 못했습니다:", error);
      }
    };
    fetchData(); // 데이터 가져오기 함수 호출
  }, []);

  const parents = [];
  const children = [];

  // 부모와 자식을 그룹화
  familyData.forEach((family) => {
    if (family.role === "ROLE_PARENT") {
      parents.push(family);
    } else if (family.role === "ROLE_CHILD") {
      children.push(family);
    }
  });

  return (
    <motion.div
      initial={Wrapper.initial}
      animate={Wrapper.animate}
      exit={Wrapper.exit}>
      <div className="myfamily-container">
        <div>
          <Profile />
        </div>
        <ChangeFamilyName />
        <div className="familymenu">
          <Link to="/financial">
            <button className="family-productbutton">상품</button>
          </Link>
          <Link to="/mission">
            <button className="family-missionbutton">미션</button>
          </Link>
          <button className="family-invitation" onClick={handleAddFamilyMember}>
            <MdGroupAdd />
            초대
          </button>
          <button className="family-deletebutton" onClick={handleDeleteFamily}>
            <MdGroupOff />
            삭제
          </button>
        </div>
        {isModalOpen && (
          <div className="modal">
            <div className="modal-content">
              <h2>닉네임 입력</h2>
              <form className="myfamily-form-arrange">
                <input
                  className="myfamily-input"
                  type="text"
                  placeholder="닉네임"
                  value={searchNickname}
                  onChange={(e) => setSearchNickname(e.target.value)}
                />
                <button
                  className="myfamily-invite-button"
                  type="button"
                  onClick={searchFamily}>
                  검색
                </button>
              </form>
              <ul>
                {searchResults.map((result) => (
                  <li key={result}>
                    {result}
                    <button
                      className="myfamily-invite-button-choose"
                      onClick={() => handleSelectFamily(result)}>
                      선택
                    </button>
                  </li>
                ))}
              </ul>
              <div className="myfamily-close-arrange-left">
                {selectedNickname && (
                  <button
                    className="myfamily-invite-button-send"
                    onClick={handleRequest}>
                    요청보내기
                  </button>
                )}
                <button
                  className="myfamily-invite-button-close"
                  onClick={() => {
                    setModalOpen(false);
                    setSearchResults([]);
                    setSearchNickname("");
                  }}>
                  닫기
                </button>
              </div>
            </div>
          </div>
        )}
        <FamilyList />
        <div className="myfamily-footer">
          <Footer />
        </div>
      </div>
    </motion.div>
  );
}

export default MyFamily;

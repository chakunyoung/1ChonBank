import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { setDeleteFamily } from "redux/Family";
import apis from "services/api/apis";
import ChangeFamilyName from "./ChangeFamilyName";
import ShowMyFamily from "./ShowMyFamily";
import Profile from "components/common/Profile";
import Footer from "components/common/Footer";
import { MdGroupAdd, MdGroupOff } from "react-icons/md";
import './myfamily.css';


function MyFamily() {

    const familyName = useSelector((state) => state.family.familyName);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    console.log("familyName:", familyName);

    const handleDeleteFamily = () => {
        const confirmed = window.confirm("정말 삭제하시겠습니까?");
        if (confirmed) {
            dispatch(setDeleteFamily());
            apis.delete(`/api/families`)
                .then((response) => {
                    alert("삭제되었습니다.")
                    console.log(response.data);
                    navigate("/");
                });
        }

    }

    const [isModalOpen, setModalOpen] = useState(false);
    const [searchNickname, setSearchNickname] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [selectedNickname, setSelectedNickname] = useState(''); // 선택한 닉네임 상태 추가

    const handleSelectFamily = (nickname) => {
        console.log('선택한 닉네임:', nickname);
        setSelectedNickname(nickname); // 선택한 닉네임 설정
    };

    const handleAddFamilyMember = () => {
        setModalOpen(true);
    };

    const handleRequest = () => {
        apis.post(`/api/families/invitation/${selectedNickname}`)
            .then((response) => {
                console.log(response.data);
            })
        // 선택한 닉네임을 이용하여 요청을 보내는 로직을 추가하세요.
        // console.log('선택한 닉네임을 이용하여 요청을 보냅니다:', selectedNickname);
        setModalOpen(false); // "요청보내기" 버튼을 눌렀을 때 모달을 닫습니다.
    };

    const searchFamily = () => {
        apis.get(`/api/user/findFamily/${searchNickname}`)

            .then((response) => {
                console.log(response.data.data)

                if (Array.isArray(response.data.data)) {
                    setSearchResults(response.data.data);
                } else {
                    console.error('API 응답이 배열 형식이 아닙니다.');
                }
            })
            .catch((error) => {
                console.error('검색 오류:', error);
            });
    };

    return (
        <div className="myfamily-container">
            <div><Profile/></div>
            <ChangeFamilyName />
            <div className="familymenu">
            <Link to="/financial"><button className="family-productbutton">상품</button></Link>
            <Link to="/mission"><button className="family-missionbutton">미션</button></Link>
            <button className="family-deletebutton" onClick={handleDeleteFamily}><MdGroupOff/>삭제</button>
            <button className="family-invitation" onClick={handleAddFamilyMember}><MdGroupAdd/>초대</button>
            </div>
            {isModalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>닉네임 입력</h2>
                        <form>
                            <input
                                type="text"
                                placeholder="닉네임"
                                value={searchNickname}
                                onChange={(e) => setSearchNickname(e.target.value)}
                            />
                            <button type="button" onClick={searchFamily}>검색</button>
                        </form>
                        <ul>
                            {searchResults.map((result) => (
                               
                                <li key={result}>
                                    {result}
                                    <button onClick={() => handleSelectFamily(result)}>선택</button>
                                </li>
                            ))}
                        </ul>
                        {selectedNickname && (
                            <button onClick={handleRequest}>요청보내기</button>
                        )}
                        <button onClick={() => setModalOpen(false)}>닫기</button>
                    </div>
                </div>
            )}
            <ShowMyFamily />
            <div className='myfamily-footer'>
        <Footer/>
      </div>
        </div>
    )
}

export default MyFamily;

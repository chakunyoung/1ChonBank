import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";
import { setFamilyName } from "redux/Family";
import apis from "services/api/apis";

function CreateFamily() {

  const familyName = useSelector((state) => state.family.familyName);
  const nickname = useSelector((state) => state.auth.nickname);
  const dispatch = useDispatch();

  const navigate = useNavigate();
  const handleCreateFamily = (e) => {
    if(familyName===''){
      alert("한글자 이상 입력하세요.");
      return;
    }
    
    // familyName 변수를 이용하여 경로를 구성하여 POST 요청을 보냅니다.
    apis.post(`/api/families/${familyName}`)
      .then((response) => {
        // 성공적인 응답 처리
        alert("가족 생성을 축하드립니다!");
        console.log(response.data);
        navigate("/");
      })
      .catch((error) => {
        // 오류 처리
        console.error(error);
      });
  };

  const handleSetFamilyName = (e) => {
    dispatch(setFamilyName(e.target.value));
  }

  return (
    <div>
      <input type="text" placeholder="가족명 입력란" value={familyName}
        onChange={handleSetFamilyName}></input>
      <button onClick={handleCreateFamily}>생성</button>
      나<br />
      {nickname}<br />
      내 프로필 사진이랑 돈 (리덕스에서 가져올 예정)<br />
      <br />

    </div>
  )
}

export default CreateFamily

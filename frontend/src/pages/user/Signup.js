import { useSelector } from "react-redux";

function Signup() {
    // const dispatch = useDispatch();
    // 핸들러 메서드를 통해서 입력받는 roles랑 서버닉네임 저장
    // 저장하고 redux에도 저장
    const signupHandler=(e)=>{
      e.preventDefault();
    }

  
    return (
<div>
  <h1>
  <label>
    <input type="radio" name="relationship" value="부모" /> 부모
  </label>
  <label>
    <input type="radio" name="relationship" value="자식" /> 자식
  </label>
  <input type="text" id="serverNickname" placeholder="사용할 닉네임을 입력 해주세요"></input>
  <button onClick={signupHandler}> 회원가입 </button>
  </h1>
</div>
    );
  }
  export default Signup;
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import RoutePath from 'routes/RoutePath'; // 라우트 컴포넌트를 가져옵니다.


function App() {
  return (
    <Router>
      <div className="App">
        <RoutePath /> {/* 라우트 컴포넌트를 여기에 추가합니다 */}
      </div>
    </Router>
  );
}

export default App;

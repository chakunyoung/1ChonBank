import { BrowserRouter as Router, Route, Routes } from "react-router-dom";



function App() {
  return (

    <Router>
      <div className="App">
        <div>시작</div>
        <Routes>
          {/* 예시 */}
            {/* <Route path="/" element={<Home />}></Route> */}

        </Routes>
      </div>
    </Router>
  );
}

export default App;

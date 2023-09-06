import { combineReducers, configureStore } from "@reduxjs/toolkit";
import authReducer from "redux/Auth"; 

const reducers = combineReducers({
    auth: authReducer,
    // 여기에 다른 리듀서들을 추가할 수 있습니다. 예: boardList: boardListSlice,
});

const store = configureStore({
  reducer: reducers,
  // getDefaultMiddleware로 기본 미들웨어를 포함하고 로거 추가
});

export default store;

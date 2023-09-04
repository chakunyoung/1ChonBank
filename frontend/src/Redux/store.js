import { combineReducers, configureStore } from "@reduxjs/toolkit";

// import boardListSlice from "./boardListSlice";
const  reducers = combineReducers({
    // 예시
    // boardList: boardListSlice,

});

// const persistConfig = {
//   key: "root",
//   storage,
//   whitelist: ["login"]
// };


// const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  // 리듀서 등록
//   reducer: persistedReducer,
  // getDefaultMiddleware로 기본 미들웨어를 포함하고 로거 추가
});

export default store;

import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; 
import authReducer from "redux/Auth"; 
import Finance from "./Finance";

import QuizInfo from "./QuizInfo";
import Depositor from "./Depositor";
import Savingser from "./Savingser";
import Loaner from "./Loaner";
import Family from "./Family";


const persistConfig = {
  key: 'root',
  storage,
};

const reducers = combineReducers({
  auth: authReducer,
  finance: Finance,
  depositor : Depositor,
  savingser : Savingser,
  loaner : Loaner,
  quizInfo: QuizInfo, 
  family : Family,

  // 여기에 다른 리듀서들을 추가할 수 있습니다. 예: boardList: boardListSlice,
});

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

let persistor = persistStore(store);

export { store, persistor };

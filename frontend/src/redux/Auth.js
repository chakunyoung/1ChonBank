import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { useDispatch } from "react-redux";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
  user: null,
  firebaseToken: "",
  accessToken: "",
  refreshToken: "",
};

export const login = createAsyncThunk(
  "auth/login",
  async (data, { rejectWithValue }) => {
    try {
      const res = await axios.post("/api/users/login", data, {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const getUserInfo = createAsyncThunk(
  "auth/getUserInfo",
  async (accessToken, { rejectWithValue }) => {
    try {
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      const res = await axios.get("/api/mypage", {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

// 카카오 id_token 정보로, 우리서버 유저 정보 만들기
export const kakaoLogin = createAsyncThunk(
  "auth/kakaoLogin",
  async (id_token, { rejectWithValue }) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${id_token}`;
    try {
      const res = await axios.post("/api/users/login/kakao", {});
      return res.data.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser(state, action) {
      state.user = action.payload;
    },
    setFirebaseToken: (state, action) => {
      state.firebaseToken = action.payload;
    },

    // logout: (state, action) => {
    //   state.refreshToken = '';
    //   state.userId = '';
    //   state.isLogin = false;
    //   state.type = '';
    //   state.accessToken = '';
    //   state.roles = '';
    //   state.nickname = '';
    //   state.serverNickname = '';
    // },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.fulfilled, (state, { payload }) => {
        state.accessToken = payload.data["access-token"];
        state.refreshToken = payload.data["refresh-token"];
      })
      .addCase(getUserInfo.fulfilled, (state, { payload }) => {
        state.user = payload.data;
      })
      .addCase(kakaoLogin.fulfilled, (state, { payload }) => {
        console.log(payload);
        state.accessToken = payload[`access-token`];
        state.refreshToken = payload[`refresh-token`];
      });
  },
});

// export const { setAccessToken, setMoney,setScore, setRoles, setType, setUserId, setIsLogin,setRefreshToken,setNickname,setServerNickname, logout,setQuiz} = authSlice.actions;
export const { setUser, setFirebaseToken } = authSlice.actions;

export default authSlice.reducer;

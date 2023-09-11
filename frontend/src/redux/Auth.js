import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { useDispatch } from "react-redux";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
  refreshToken: '',
  userId: '',
  isLogin: false,
  type : '',
  accessToken: '',
  roles : '',
  nickname:'',
  serverNickname:'',
};

export const login = createAsyncThunk(
  
  "auth/login",
  async (data, { rejectWithValue }) => {
    try {
      const res = await axios.post("/api/users/login", data, {
      });
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
      const res = await axios.get("/api/mypage", {
      });
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const kakaoLogin = createAsyncThunk(
  "auth/kakaoLogin",
  async (accessToken, { rejectWithValue }) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    try {
      const res = await axios.post("/api/users/login/kakao", {
      });
      console.log(res.data['access-token']);
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setAccessToken(state, action) {
      state.accessToken = action.payload;
    },

    setRoles(state, action) {
      state.roles = action.payload;
    },

    setType(state, action) {
      state.type = action.payload;
    },

    setUserId(state, action) {
      state.userId = action.payload;
    },

    setIsLogin: (state, action) => {
      state.isLogin = action.payload;
    },

    setRefreshToken: (state, action) => {
      state.refreshToken = action.payload;
    },

    setNickname:(state, action)=>{
      state.nickname = action.payload;
    },

    setServerNickname: (state, action) => {
      state.serverNickname = action.payload;
    },
    
    logout: (state, action) => {
      state.refreshToken = '';
      state.userId = '';
      state.isLogin = false;
      state.type = '';
      state.accessToken = '';
      state.roles = '';
      state.nickname = '';
      state.serverNickname = '';
    },
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
        state.accessToken = payload.data["access-token"];
        state.refreshToken = payload.data["refresh-token"];
      });
  },
});

export const { setAccessToken, setRoles, setType, setUserId, setIsLogin,setRefreshToken,setNickname,setServerNickname, logout} = authSlice.actions;

export default authSlice.reducer;

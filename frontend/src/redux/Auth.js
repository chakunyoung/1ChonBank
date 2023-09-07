import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
  accessToken: "",
  refreshToken: "",
  user: null,
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
    loginSuccess: (state, action) => {
      state.user = action.payload;
    },
    loginFailure: (state, action) => {
      state.user = null;
    },
    logout: (state, action) => {
      state.user = null;
      state.accessToken = null;
      state.refreshToken = null;
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

export const { loginSuccess, loginFailure, logout } = authSlice.actions;

export default authSlice.reducer;

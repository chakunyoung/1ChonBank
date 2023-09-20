import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { useDispatch } from "react-redux";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
    mission: null,
  
};


const authSlice = createSlice({
  name: "mission",
  initialState,
  reducers: {
    setMission(state, action) {
      state.mission = action.payload;
    },
  },
});

export const { setMission } = authSlice.actions;

export default authSlice.reducer;

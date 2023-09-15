import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "services/api/apis";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
    familyName : '',
    
};

const familySlice = createSlice({
    name: "family",
    initialState,
    reducers: {
      setFamilyName(state, action) {
        state.familyName = action.payload;
      },

      setDeleteFamily(state){
        state.familyName="";
      }
    },
  });

export const { setFamilyName,setDeleteFamily } = familySlice.actions;

export default familySlice.reducer;
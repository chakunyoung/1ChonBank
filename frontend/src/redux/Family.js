import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";

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
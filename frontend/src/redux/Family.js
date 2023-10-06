import { createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";

const initialState = {
  familyName: "",
  familyId: "",
  familyMember: [],
};
export const getFamilyMembers = async () => {
  const response = await apis.get("/api/families");
  return response.data.data;
};
const familySlice = createSlice({
  name: "family",
  initialState,
  reducers: {
    setFamilyMember(state, action) {
      state.familyMember = action.payload;
    },

    setFamilyId(state, action) {
      state.familyId = action.payload;
    },

    setFamilyName(state, action) {
      state.familyName = action.payload;
    },

    setDeleteFamily(state) {
      state.familyName = "";
    },
  },
});

export const { setFamilyMember, setFamilyName, setDeleteFamily, setFamilyId } =
  familySlice.actions;

export default familySlice.reducer;

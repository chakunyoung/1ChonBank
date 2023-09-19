import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";

const initialState = {
    id:'',
	userId:'',
	financialProductId:'',
	productName:'',
	grant:'',
	money:'',
	date:'',
    regularMoney: '',
};
export const makeSavingser = createAsyncThunk(
    "loaner/savingser",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.post("/api/savingser", JSON.stringify(data), {
            });
            console.log(res.data);
        } catch (error) {
            rejectWithValue(error.response.data);
        }
    }
);

const savingserSlice = createSlice({
    name: "savingser",
    initialState,
    reducers: {
        setSavingserId(state, action) {
            state.id = action.payload;
        },
        setSavingserUserId(state, action) {
            state.userId = action.payload;
        },
        setSavingserFinancialProductId(state, action) {
            state.financialProductId = action.payload;
        },
        setSavingserProductName(state, action) {
            state.productName = action.payload;
        },
        setSavingserGrant(state, action) {
            state.grant = action.payload;
        },
        setSavingserMoney(state, action) {
            state.money = action.payload;
        },
        setSavingserDate(state, action) {
            state.date = action.payload;
        },
        setSavingserRegularMoney(state, action) {
            state.regularMoney = action.payload;
        },
        
    },
});

export const { setSavingserId, setSavingserUserId, setSavingserFinancialProductId, setSavingserProductName, setSavingserGrant, setSavingserMoney, setSavingserDate, setSavingserRegularMoney } = savingserSlice.actions;

export default savingserSlice.reducer;
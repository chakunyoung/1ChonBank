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
};
export const makeLoaner = createAsyncThunk(
    "loaner/makeLoaner",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.post("/api/loaner", JSON.stringify(data), {
            });
            console.log(res.data);
        } catch (error) {
            rejectWithValue(error.response.data);
        }
    }
);

const loanerSlice = createSlice({
    name: "loaner",
    initialState,
    reducers: {
        setId(state, action) {
            state.id = action.payload;
        },
        setUserId(state, action) {
            state.userId = action.payload;
        },
        setFinancialProductId(state, action) {
            state.financialProductId = action.payload;
        },
        setProductName(state, action) {
            state.productName = action.payload;
        },
        setGrant(state, action) {
            state.grant = action.payload;
        },
        setMoney(state, action) {
            state.money = action.payload;
        },
        setDate(state, action) {
            state.date = action.payload;
        },
    },
});

export const { setId, setUserId, setFinancialProductId, setProductName, setGrant, setMoney, setDate } = loanerSlice.actions;

export default loanerSlice.reducer;
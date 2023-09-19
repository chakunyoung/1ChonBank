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
        setLoanerId(state, action) {
            state.id = action.payload;
        },
        setLoanerUserId(state, action) {
            state.userId = action.payload;
        },
        setLoanerFinancialProductId(state, action) {
            state.financialProductId = action.payload;
        },
        setLoanerProductName(state, action) {
            state.productName = action.payload;
        },
        setLoanerGrant(state, action) {
            state.grant = action.payload;
        },
        setLoanerMoney(state, action) {
            state.money = action.payload;
        },
        setLoanerDate(state, action) {
            state.date = action.payload;
        },
    },
});

export const { setLoanerId, setLoanerUserId, setLoanerFinancialProductId, setLoanerProductName, setLoanerGrant, setLoanerMoney, setLoanerDate } = loanerSlice.actions;

export default loanerSlice.reducer;
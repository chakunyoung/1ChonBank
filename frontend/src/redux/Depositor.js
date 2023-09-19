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
export const makeDepositor = createAsyncThunk(
    "depositor/makeDepositor",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.post("/api/depositor", JSON.stringify(data), {
            });
            console.log(res.data);
        } catch (error) {
            rejectWithValue(error.response.data);
        }
    }
);

const depositorSlice = createSlice({
    name: "depositor",
    initialState,
    reducers: {
        setDepositorId(state, action) {
            state.id = action.payload;
        },
        setDepositorUserId(state, action) {
            state.userId = action.payload;
        },
        setDepositorFinancialProductId(state, action) {
            state.financialProductId = action.payload;
        },
        setDepositorProductName(state, action) {
            state.productName = action.payload;
        },
        setDepositorGrant(state, action) {
            state.grant = action.payload;
        },
        setDepositorMoney(state, action) {
            state.money = action.payload;
        },
        setDepositorDate(state, action) {
            state.date = action.payload;
        },
    },
});

export const { setDepositorId, setDepositorUserId, setDepositorFinancialProductId, setDepositorProductName, setDepositorGrant, setDepositorMoney, setDepositorDate } = depositorSlice.actions;

export default depositorSlice.reducer;
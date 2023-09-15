import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "services/api/apis";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

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
    "finance/makeDepositor",
    async (data, { rejectWithValue }) => {
        try {
            const res = await axios.post("/api/depositor", JSON.stringify(data), {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

const depositorSlice = createSlice({
    name: "auth",
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

export const { setId, setUserId, setFinancialProductId, setProductName, setGrant, setMoney, setDate } = depositorSlice.actions;

export default depositorSlice.reducer;
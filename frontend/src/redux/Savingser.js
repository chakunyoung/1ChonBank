import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "services/api/apis";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
    id: '',
	userId: '',
	financialProductId: '',
	productName: '',
	grant: '',
	money: '',
	date: '',
	regularMoney: '',
};
export const makeSavingser = createAsyncThunk(
    "savingser/makeSavingser",
    async (data, { rejectWithValue }) => {
        try {
            const res = await axios.post("/api/savingser", JSON.stringify(data), {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

const savingserSlice = createSlice({
    name: "savingser",
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
        setRegularMoney(state, action) {
            state.regularMoney = action.payload;
        },
    },
});

export const { setId, setUserId, setFinancialProductId, setProductName, setGrant, setMoney, setDate, setRegularMoney } = savingserSlice.actions;

export default savingserSlice.reducer;
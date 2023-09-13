import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { useSelector } from "react-redux";
import axios from "services/api/apis";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = {
    id: '0',
    parentId: '1',
    familyId: '1',
    name: '1',
    rate: '1',
    info: '1',
    period: '1',
    productType: '1',
};
export const makeProduct = createAsyncThunk(
    "finance/makeProduct",
    async (data, { rejectWithValue }) => {
        try {
            const res = await axios.post("/api/financial", JSON.stringify(data), {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);
export const getProductList = createAsyncThunk(
    "finance/getProductList",
    async (data, { rejectWithValue }) => {
        try {
            const res = await axios.get("/api/financial/1", {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);


const financeSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setParentId(state, action) {
            state.parentId = action.payload;
        },
        setFamilyId(state, action) {
            state.familyId = action.payload;
        },
        setProductType(state, action) {
            state.productType = action.payload;
        },
        setName(state, action) {
            state.name = action.payload;
        },
        setInfo(state, action) {
            state.info = action.payload;
        },
        setPeriod(state, action) {
            state.period = action.payload;
        },
        setRate(state, action) {
            state.rate = action.payload;
        },
        resetAll(state) {
            state.id = '0';
            state.parentId = '';
            state.familyId = '';
            state.name = '';
            state.rate = '';
            state.info = '';
            state.period = '';
            state.productType = '';
        },
    },
});

export const { resetAll, setParentId, setFamilyId, setProductType, setName, setInfo, setPeriod, setRate } = financeSlice.actions;

export default financeSlice.reducer;
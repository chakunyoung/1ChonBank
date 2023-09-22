import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";


const initialState = {
    data: {
        id: '',
        parentId: '',
        familyId: '',
        name: '',
        rate: '',
        info: '',
        period: '',
        productType: '',
    },
};
export const makeProduct = createAsyncThunk(
    "finance/makeProduct",
    async (data, { rejectWithValue }) => {
        try {
            console.log(data);
            const res = await apis.post("/api/financial", data, {
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
            const res = await apis.get("/api/financial", {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);


const financeSlice = createSlice({
    name: "finance",
    initialState,
    reducers: {
        setParentId(state, action) {
            state.data.parentId = action.payload;
        },
        setFamilyId(state, action) {
            state.data.familyId = action.payload;
        },
        setProductType(state, action) {
            state.data.productType = action.payload;
        },
        setName(state, action) {
            state.data.name = action.payload;
        },
        setInfo(state, action) {
            state.data.info = action.payload;
        },
        setPeriod(state, action) {
            state.data.period = action.payload;
        },
        setRate(state, action) {
            state.data.rate = action.payload;
        },
        resetAll(state) {
            state.data.id = '0';
            state.data.parentId = '';
            state.data.familyId = '';
            state.data.name = '';
            state.data.rate = '';
            state.data.info = '';
            state.data.period = '';
            state.data.productType = '';
        },
        setAll(state, action) {
            const {
                id,
                parentId,
                familyId,
                name,
                rate,
                info,
                period,
                productType,
            } = action.payload;
            state.data.id = id;
            state.data.parentId = parentId;
            state.data.familyId = familyId;
            state.data.name = name;
            state.data.rate = rate;
            state.data.info = info;
            state.data.period = period;
            state.data.productType = productType;
        },
    },
});

export const { setAll, resetAll, setParentId, setFamilyId, setProductType, setName, setInfo, setPeriod, setRate } = financeSlice.actions;

export default financeSlice.reducer;
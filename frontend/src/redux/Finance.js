import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";


const initialState = {
    id: '',
    parentId: '',
    familyId: '',
    name: '',
    rate: '',
    info: '',
    period: '',
    productType: '',
};
export const makeProduct = createAsyncThunk(
    "finance/makeProduct",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.post("/api/financial", JSON.stringify(data), {
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
            const res = await apis.get("/api/financial/" + data, {
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
            state.id = id;
            state.parentId = parentId;
            state.familyId = familyId;
            state.name = name;
            state.rate = rate;
            state.info = info;
            state.period = period;
            state.productType = productType;
        },
    },
});

export const { setAll, resetAll, setParentId, setFamilyId, setProductType, setName, setInfo, setPeriod, setRate } = financeSlice.actions;

export default financeSlice.reducer;
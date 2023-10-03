import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";

const initialState = {
    data: {
        id: '',
        userId: '',
        userNickname:'',
        financialProductId: '',
        productName: '',
        grant: '',
        money: '',
        cardNumber: '',
        date: '',
        expiry: '',
        regularMoney: '',
    },
};

export const makeSavingser = createAsyncThunk(
    "savingser/makeSavingser",
    async (data, { rejectWithValue }) => {
        try {
            console.log(data);
            const res = await apis.post("/api/savingser", data, {
            });
            console.log(res.data);
        } catch (error) {
            rejectWithValue(error.response.data);
        }
    }
);

export const getSavingser = createAsyncThunk(
    "savingser/getSavingser",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.get("/api/savingser/disallowList", {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);
export const getSavingserByNickname = createAsyncThunk(
    "savingser/getSavingserByNickname",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.get("/api/savingser/Custommer/" + data, {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);
export const getSavCustomer = createAsyncThunk(
    "savingser/getSavCustomer",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.get("/api/savingser/disallowCustommer/" + data, {
            });
            console.log(res);
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const allowSavingser = createAsyncThunk(
    "savingser/allowSavingser",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.put("/api/savingser/allow/" + data, {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const refuseSavingser = createAsyncThunk(
    "savingser/refuseSavingser",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.put("/api/savingser/refuse/" + data, {
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
        setSavingserId(state, action) {
            state.data.id = action.payload;
        },
        setSavingserUserId(state, action) {
            state.data.userId = action.payload;
        },
        setSavingserFinancialProductId(state, action) {
            state.data.financialProductId = action.payload;
        },
        setSavingserProductName(state, action) {
            state.data.productName = action.payload;
        },
        setSavingserGrant(state, action) {
            state.data.grant = action.payload;
        },
        setSavingserMoney(state, action) {
            state.data.money = action.payload;
        },
        setSavingserDate(state, action) {
            state.data.date = action.payload;
        },
        setSavingserRegularMoney(state, action) {
            state.data.regularMoney = action.payload;
        },

    },
});

export const { setSavingserId, setSavingserUserId, setSavingserFinancialProductId, setSavingserProductName, setSavingserGrant, setSavingserMoney, setSavingserDate, setSavingserRegularMoney } = savingserSlice.actions;

export default savingserSlice.reducer;
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
    },
};
export const makeLoaner = createAsyncThunk(
    "loaner/makeLoaner",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.post("/api/loaner", data, {
            });
            console.log(res.data);
        } catch (error) {
            rejectWithValue(error.response.data);
        }
    }
);
export const getLoaner = createAsyncThunk(
    "loaner/getLoaner",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.get("/api/loaner/disallowList", {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const getLoaCustomer = createAsyncThunk(
    "loaner/getLoaCustomer",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.get("/api/loaner/disallowCustommer/" + data, {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const allowLoaner = createAsyncThunk(
    "loaner/allowLoaner",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.put("/api/loaner/allow/" + data, {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);
export const refuseLoaner = createAsyncThunk(
    "loaner/refuseLoaner",
    async (data, { rejectWithValue }) => {
        try {
            const res = await apis.put("/api/loaner/refuse/" + data, {
            });
            return res.data;
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

const loanerSlice = createSlice({
    name: "loaner",
    initialState,
    reducers: {
        setLoanerId(state, action) {
            state.data.id = action.payload;
        },
        setLoanerUserId(state, action) {
            state.data.userId = action.payload;
        },
        setLoanerFinancialProductId(state, action) {
            state.data.financialProductId = action.payload;
        },
        setLoanerProductName(state, action) {
            state.data.productName = action.payload;
        },
        setLoanerGrant(state, action) {
            state.data.grant = action.payload;
        },
        setLoanerMoney(state, action) {
            state.data.money = action.payload;
        },
        setLoanerDate(state, action) {
            state.data.date = action.payload;
        },
    },
});

export const { setLoanerId, setLoanerUserId, setLoanerFinancialProductId, setLoanerProductName, setLoanerGrant, setLoanerMoney, setLoanerDate } = loanerSlice.actions;

export default loanerSlice.reducer;
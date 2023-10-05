import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import apis from "services/api/apis";

const initialState = {
  data: {
    id: "",
    userId: "",
    financialProductId: "",
    productName: "",
    grant: "",
    money: "",
    cardNumber: "",
    date: "",
    expiry: "",
  },
};

export const makeDepositor = createAsyncThunk(
  "depositor/makeDepositor",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.post("/api/depositor", data, {});
      return res.data;
    } catch (error) {
      rejectWithValue(error.response.data);
    }
  }
);

export const getDepositor = createAsyncThunk(
  "depositor/getDepositor",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.get("/api/depositor/disallowList", {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const getDepositorByNickname = createAsyncThunk(
  "depositor/getDepositorByNickname",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.get("/api/depositor/Custommer/" + data, {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const getDepCustomer = createAsyncThunk(
  "depositor/getDepCustomer",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.get(
        "/api/depositor/disallowCustommer/" + data,
        {}
      );
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const allowDepositor = createAsyncThunk(
  "depositor/allowDepositor",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.put("/api/depositor/allow/" + data, {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const refuseDepositor = createAsyncThunk(
  "depositor/refuseDepositor",
  async (data, { rejectWithValue }) => {
    try {
      const res = await apis.put("/api/depositor/refuse/" + data, {});
      return res.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const depositorSlice = createSlice({
  name: "depositor",
  initialState,
  reducers: {
    setDepositorId(state, action) {
      state.data.id = action.payload;
    },
    setDepositorUserId(state, action) {
      state.data.userId = action.payload;
    },
    setDepositorFinancialProductId(state, action) {
      state.data.financialProductId = action.payload;
    },
    setDepositorProductName(state, action) {
      state.data.productName = action.payload;
    },
    setDepositorGrant(state, action) {
      state.data.grant = action.payload;
    },
    setDepositorMoney(state, action) {
      state.data.money = action.payload;
    },
    setDepositorDate(state, action) {
      state.data.date = action.payload;
    },
  },
});

export const {
  setDepositorId,
  setDepositorUserId,
  setDepositorFinancialProductId,
  setDepositorProductName,
  setDepositorGrant,
  setDepositorMoney,
  setDepositorDate,
} = depositorSlice.actions;

export default depositorSlice.reducer;

import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

axios.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
axios.defaults.withCredentials = true;

const initialState = { 
    quizInfo:null,
            
}

const quizSlice = createSlice({
    name: "quizInfo",
    initialState,
    reducers: {
        setQuizInfo(state,action){
            state.quizInfo=action.payload;
        },
    }
})

export const {setQuizInfo} = quizSlice.actions;

export default quizSlice.reducer;
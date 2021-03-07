import {
    CLEAR_STATE,
    LOGIN_STUDENT,
    UPDATE_ERROR,ADD_SUGGESTION,SUGGESTION_CHANGED
} from "../actions/types";

const initialState = {
    suggestion:'',
    errorMessage: '',
    apiKey:'',
};


export default (state = initialState, action) => {
    switch (action.type) {
        case SUGGESTION_CHANGED:
            return { ...state, suggestion: action.payload };
        case UPDATE_ERROR:
            return { ...state, errorMessage: action.payload};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload};
        case ADD_SUGGESTION:
            return {...state,errorMessage:action.payload};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
};
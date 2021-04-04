import {
    CLEAR_STATE,
    LOGIN_STUDENT,
    UPDATE_ERROR_ADD_SUGGESTION,
    ADD_SUGGESTION,SUGGESTION_CHANGED,
    WAIT_FOR_ADD_SUGGESTION,
} from "../actions/types";

const initialState = {
    suggestion:'',
    errorMessage: '',
    apiKey:'',
    loading: false,
};


export default (state = initialState, action) => {
    switch (action.type) {
        case SUGGESTION_CHANGED:
            return { ...state, suggestion: action.payload };
        case UPDATE_ERROR_ADD_SUGGESTION:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false,};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload};
        case ADD_SUGGESTION:
            return {...state,errorMessage:''};
        case WAIT_FOR_ADD_SUGGESTION:
            return {...state, errorMessage:'', loading: true};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
};
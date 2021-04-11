import {
    ALIAS_CHANGED_RESET_PASSWORD,
    UPDATE_ERROR_RESET_PASSWORD,
    CLEAR_STATE,
    RESET_PASSWORD_WAIT,
    RESET_PASSWORD_FINISHED,
  } from '../actions/types';

const initialState = {
    alias:'',
    errorMessage: '',
    loading: false,
};


export default (state = initialState, action) => {
    switch (action.type) {
        case ALIAS_CHANGED_RESET_PASSWORD:
            return { ...state, alias: action.payload };
        case UPDATE_ERROR_RESET_PASSWORD:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false};
        case RESET_PASSWORD_WAIT:
            return {...state, loading: true, errorMessage:''};
        case RESET_PASSWORD_FINISHED:
            return {...initialState};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
};
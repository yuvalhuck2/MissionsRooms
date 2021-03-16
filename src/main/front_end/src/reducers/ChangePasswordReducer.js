const initialState = {
    password:'',
    errorMessage: '',
    apiKey:'',
    loading:false,
  };

  import {
    UPDATE_ERROR_CHANGE_PASSWORD,
    RESET_CHANGE_PASSWORD,
    NEW_PASSWORD_CHANGED,
    WAIT_FOR_CHANGE_PASSWORD,
    LOGIN_STUDENT,
    LOGIN_TEACHER,
    LOGIN_IT,
    CLEAR_STATE,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case NEW_PASSWORD_CHANGED:
            return { ...state, password: action.payload };
        case LOGIN_STUDENT:
        case LOGIN_TEACHER:
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload};
        case RESET_CHANGE_PASSWORD:
            return { ...initialState, apiKey: action.payload};
        case WAIT_FOR_CHANGE_PASSWORD:
            return {...state, loading:true}
        case UPDATE_ERROR_CHANGE_PASSWORD:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };
const initialState = {
    alias: '',
    password: '',
    loading: false,
    errorMessage: '',
    apiKey: '',
  };
  import {
    CLEAR_STATE,
    ALIAS_IT_CHANGED,
    ADD_IT,
    PASSWORD_IT__CHANGED,
    LOGIN_IT,
    UPDATE_ADD_IT_ERROR,
    ADD_IT_RESET,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
      case ALIAS_IT_CHANGED:
        return { ...state, alias: action.payload };
      case PASSWORD_IT__CHANGED:
        return { ...state, password: action.payload };
      case UPDATE_ADD_IT_ERROR:
          alert(action.payload)
        return { ...state, errorMessage: action.payload, loading: false };
      case ADD_IT:
        return {
          ...state,
          loading: true,
          errorMessage: '',
        };
      case LOGIN_IT:
        return { ...initialState, apiKey: action.payload};
      case ADD_IT_RESET:
        return {... initialState, apiKey: state.apiKey}
      case CLEAR_STATE:
        return initialState;
      default:
        return state;
    }
  };
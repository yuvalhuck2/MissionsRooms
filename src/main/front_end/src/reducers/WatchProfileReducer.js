const initialState = {
    search: '',
    profile:'',
    allUsers:[],
    presentedUsers:[],
    errorMessage: '',
    apiKey:'',
    message:'',
    loading:false,
  };

  import {
    SEARCH_CHANGED,
    MESSAGE_CHANGED,
    UPDATE_ERROR_WATCH_PROFILE,
    LOGIN_STUDENT,
    LOGIN_TEACHER,
    LOGIN_IT,
    USERS_CHANGED,
    RESET_WATCH_PROFILE,
    PROFILE_CHANGED,
    WAIT_FOR_MESSAGE,
    MESSAGE_SENT,
    UPDATE_ALL_USER_PROFILES,
    CLEAR_STATE,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_CHANGED:
            return { ...state, search: action.payload };
        case MESSAGE_CHANGED:
            return { ...state, message: action.payload };
        case USERS_CHANGED:
            return {...state, presentedUsers: action.payload}
        case LOGIN_STUDENT:
        case LOGIN_TEACHER:
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload};
        case RESET_WATCH_PROFILE:
            return { ...initialState, apiKey: action.payload};
        case PROFILE_CHANGED:
            return {...state, profile: action.payload}
        case WAIT_FOR_MESSAGE:
            return {...state, loading:true}
        case MESSAGE_SENT:
            return {...state, loading:false, message:""}
        case UPDATE_ALL_USER_PROFILES:
            return {... state, allUsers: action.payload, presentedUsers: action.payload}
        case UPDATE_ERROR_WATCH_PROFILE:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };
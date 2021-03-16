const initialState = {
    search: '',
    message:{},
    allMessages:[],
    presentedMessages:[],
    errorMessage: '',
    apiKey:'',
    loading:false,
  };

  import {
    SEARCH_WATCH_MESSAGES_CHANGED,
    CHOOSE_MESSAGE,
    UPDATE_ERROR_WATCH_MESSAGES,
    LOGIN_STUDENT,
    LOGIN_TEACHER,
    LOGIN_IT,
    PRESENTED_MESSAGES_CHANGED,
    RESET_WATCH_MESSAGES,
    WAIT_FOR_DELETE_MESSAGE,
    MESSAGE_DELETED,
    UPDATE_ALL_MESSAGES,
    CLEAR_STATE,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_WATCH_MESSAGES_CHANGED:
            return { ...state, search: action.payload };
        case CHOOSE_MESSAGE:
            return { ...state, message: action.payload };
        case PRESENTED_MESSAGES_CHANGED:
            return {...state, presentedMessages: action.payload}
        case LOGIN_STUDENT:
        case LOGIN_TEACHER:
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload};
        case RESET_WATCH_MESSAGES:
            return { ...initialState, apiKey: action.payload};
        case WAIT_FOR_DELETE_MESSAGE:
            return {...state, loading:true}
        case MESSAGE_DELETED:
            return {...state, loading:false, message:{},
                allMessages:state.allMessages.filter((message) => message.id != action.payload),
                presentedMessages:state.presentedMessages.filter((message) => message.id!= action.payload)
            }
        case UPDATE_ALL_MESSAGES:
            return {... state, allMessages: action.payload, presentedMessages: action.payload}
        case UPDATE_ERROR_WATCH_MESSAGES:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };
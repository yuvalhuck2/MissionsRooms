const initialState = {
    suggestion:{},
    allSuggestions:[],
    errorMessage: '',
    apiKey:'',
    loading:false,
  };

  import {
    RESET_WATCH_SUGGESTIONS,
    CHOOSE_SUGGESTION,
    WAIT_FOR_DELETE_SUGGESTION,
    SUGGESTION_DELETED,
    UPDATE_ALL_SUGGESTIONS,
    UPDATE_ERROR_WATCH_SUGGESTIONS,
    LOGIN_STUDENT,
    LOGIN_TEACHER,
    LOGIN_IT,
    CLEAR_STATE,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case CHOOSE_SUGGESTION:
            return { ...state, suggestion: action.payload };
        case LOGIN_STUDENT:
        case LOGIN_TEACHER:
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload};
        case RESET_WATCH_SUGGESTIONS:
            return { ...initialState, apiKey: action.payload};
        case WAIT_FOR_DELETE_SUGGESTION:
            return {...state, loading:true}
        case SUGGESTION_DELETED:
            return {...state, loading:false, suggestion:{},
                allSuggestions:state.allSuggestions.filter((suggestion) => suggestion.id != action.payload)
            }
        case UPDATE_ALL_SUGGESTIONS:
            return {... state, allSuggestions: action.payload};
        case UPDATE_ERROR_WATCH_SUGGESTIONS:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };
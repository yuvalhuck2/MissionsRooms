const initialState = {
    currentRoom:undefined,
    rooms:new Map(),
    errorMessage: '',
    apiKey:'',
    loading: false,
    dialog:"",
  };

  import {
    CURRENT_ROOM_CHANGED,
    UPDATE_ERROR_CHOOSE_ROOM,
    CLEAR_STATE,
    PASS_TO_SOLVE_MISSIONS,
    GET_STUDENT_ROOMS,
    LOGIN_STUDENT,
    WAIT_FOR_ROOM_DATA,
    STUDENT_DIALOG,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case CURRENT_ROOM_CHANGED:
            return { ...state, currentRoom:state.rooms.get(action.payload)};
        case PASS_TO_SOLVE_MISSIONS:
            return { ...state, currentRoom:undefined, errorMessage: '',loading:false};
        case GET_STUDENT_ROOMS:
            return {...state, rooms:action.payload,currentRoom:undefined,};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case WAIT_FOR_ROOM_DATA:
            return { ...state, loading: true };
        case STUDENT_DIALOG:
            return { ...state, dialog: action.payload };
        case UPDATE_ERROR_CHOOSE_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };
const initialState = {
    currentMission:{loading:false},
    currentRoom:undefined,
    rooms:new Map(),
    errorMessage: '',
    apiKey:'',
  };

  import {
    SOLVE_MISSION,
    CURRENT_ROOM_CHANGED,
    UPDATE_ERROR_SOLVE_ROOM,
    CLEAR_STATE,
    PASS_TO_SOLVE_MISSIONS,
    CURRENT_ANSWER_CHANGED,
    GET_STUDENT_ROOMS,
    LOGIN_STUDENT,
    SOLVE_MISSION_SEND,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case CURRENT_ROOM_CHANGED:
            return { ...state, currentRoom:action.payload};
        case PASS_TO_SOLVE_MISSIONS:
            //console.log(state.rooms)
            return { ...state, currentMission:action.payload, errorMessage: ''};
        case CURRENT_ANSWER_CHANGED:
            return { ...state, currentMission:action.payload};
        case SOLVE_MISSION:
            return { ...state, currentMission:{...state.currentMission, loading: true,answers:[]} };
        case GET_STUDENT_ROOMS:
            return {...state, rooms:action.payload,currentRoom:undefined,};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case SOLVE_MISSION_SEND:
            return { ...state, loading: true };
        case UPDATE_ERROR_SOLVE_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, currentMission:{...state.currentMission, loading: false}};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };
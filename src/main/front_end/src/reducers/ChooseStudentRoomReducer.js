const initialState = {
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
    TRIES,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case CURRENT_ROOM_CHANGED:
            return { ...state, currentRoom:state.rooms.get(action.payload)};
        case PASS_TO_SOLVE_MISSIONS:
            return { ...state, currentRoom:{... state.currentRoom,currentMission:action.payload}, errorMessage: ''};
        case TRIES:
            state.rooms.set(state.currentRoom.roomId,state.currentRoom)
            return state;
        case CURRENT_ANSWER_CHANGED:
            return { ...state,currentRoom:{...state.currentRoom, currentMission:action.payload}};
        case SOLVE_MISSION:
            return { ...state,currentRoom:{...state.currentRoom, currentMission:{...state.currentRoom.currentMission, loading: true,answers:[]} }};
        case GET_STUDENT_ROOMS:
            return {...state, rooms:action.payload,currentRoom:undefined,};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case SOLVE_MISSION_SEND:
            return { ...state, loading: true };
        case UPDATE_ERROR_SOLVE_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, currentRoom:{...state.currentRoom,currentMission:{...state.currentRoom.currentMission, loading: false}}};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };
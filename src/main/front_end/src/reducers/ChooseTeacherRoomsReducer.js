import {
    CLEAR_STATE,
    CURRENT_ROOM_CHANGED,
    GET_STUDENT_ROOMS,
    LOGIN_STUDENT, LOGIN_TEACHER,
    PASS_TO_SOLVE_MISSIONS, STUDENT_DIALOG, UPDATE_ERROR_CHOOSE_ROOM,
    WAIT_FOR_ROOM_DATA, GET_TEACHER_ROOMS_TYPE, PASS_TO_ROOMS, ROOM_CHANGED, PASS_TO_ROOM_MENU
} from "../actions/types";

const initialState = {
    type:'',
    currentRoom:undefined,
    roomsType:[],
    presentRooms:[],
    errorMessage: '',
    apiKey:'',
    loading: false,
    dialog:"",
};


export default (state = initialState, action) => {
    switch (action.type) {
        case PASS_TO_ROOM_MENU:
            return { ...state, currentRoom:action.payload};
        case ROOM_CHANGED:
            return { ...state, currentRoom:action.payload};
        case GET_TEACHER_ROOMS_TYPE:
            return {...state, roomsType:action.payload,currentRoom:undefined,};
        case LOGIN_TEACHER:
            return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case PASS_TO_ROOMS:
            return {...state,presentRooms:action.payload};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
};
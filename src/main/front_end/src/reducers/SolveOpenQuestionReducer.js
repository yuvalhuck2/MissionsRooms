
const initialState = {fileText: '', 
                    file: undefined, 
                    mission:undefined, 
                    currentAnswer:'', 
                    isInCharge:false, 
                    loading: false, 
                    roomId:'', 
                    errorMessage: '', 
                    successMessage: '', 
                    apiKey:''};


  import {
    SOLVE_MISSION,
    CLEAR_STATE,
    CURRENT_ANSWER_CHANGED,
    LOGIN_STUDENT,
    INIT_OPEN_QUESTION_MISSION,
    EXIT_ROOM,
    FINISH_MISSION,
    CHANGE_IN_CHARGE,
    PICKED_FILE,
    RESET_FILES,
    UPDATE_ERROR,
    SEND_OPEN_ANSWER,
    FINISH_MISSION_OPEN,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case INIT_OPEN_QUESTION_MISSION:
            missionData=action.payload.roomData.currentMission
            return {... state, roomId:action.payload.roomData.roomId,loading: false, isInCharge:action.payload.isInCharge, mission:missionData}
        case PICKED_FILE:
            return {...state, file: action.payload, fileText:  action.payload.name, errorMessage: '', successMessage: ''}
        case RESET_FILES:
            return {...state, file: undefined, fileText: "", errorMessage: '', successMessage: ''}
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload };
        case SEND_OPEN_ANSWER:
            return { ...state, loading: true };
        case CURRENT_ANSWER_CHANGED:
            return { ...state,currentAnswer:action.payload};
        case CHANGE_IN_CHARGE:
            return { ...state, isInCharge: action.payload };
        case EXIT_ROOM:
            return {...initialState, apiKey:state.apiKey}
        case FINISH_MISSION:
            return { ...initialState,apiKey: action.payload, mission: state.mission }
        case UPDATE_ERROR:
            return { ...state, errorMessage: action.payload, loading: false, successMessage: '' }
        case CLEAR_STATE:
            return initialState;
        case FINISH_MISSION_OPEN:
            return {...initialState, apiKey: state.apiKey, isInCharge: action.payload.isInCharge, mission:state.mission, roomId: state.roomId}
        default:
            return state;
    }
  };
const initialState = {
    mission:undefined,
    missionsTries:new Map(),
    errorMessage: '',
    apiKey:'',
    currentAnswer:'',
    tries:3,
    isInCharge:false,
    loading: false,
    roomId:'',
  };

  import {
    SOLVE_MISSION,
    UPDATE_ERROR_SOLVE_DETERMINISTIC,
    CLEAR_STATE,
    CURRENT_ANSWER_CHANGED,
    LOGIN_STUDENT,
    SOLVE_DETERMINISTIC_MISSION_SEND,
    TRIES,
    INIT_DETEREMINISTIC,
    EXIT_ROOM,
    FINISH_MISSION,
    CHANGE_IN_CHARGE,
    UPDATE_IN_CHARGE_DETERMINISTIC,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case TRIES:
            return { ...state,tries:action.payload};
        case INIT_DETEREMINISTIC:
            missionData=action.payload.roomData.currentMission
            tries =  state.missionsTries.has(missionData.missionId) ? state.missionsTries.get(missionData.missionId) : 3
            return {... state, roomId:action.payload.roomData.roomId,loading: false, isInCharge:action.payload.isInCharge, mission:missionData, tries}
        case CURRENT_ANSWER_CHANGED:
            return { ...state,currentAnswer:action.payload};
        case SOLVE_MISSION:
            return { ...state,currentRoom:{...state.currentRoom, currentMission:{...state.currentRoom.currentMission, loading: true,answers:[]} }};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload };
        case SOLVE_DETERMINISTIC_MISSION_SEND:
            return { ...state, loading: true };
        case CHANGE_IN_CHARGE:
            return { ...state, isInCharge: action.payload };
        case EXIT_ROOM:
            if(state.mission != undefined && action.payload == state.mission.missionId){
                state.missionsTries.set(state.mission.missionId,state.tries)
            }
            return {...initialState, apiKey:state.apiKey, missionTries:state.missionsTries, mission:state.mission}
        case FINISH_MISSION:
            if(state.mission != undefined){
                state.missionsTries.set(state.mission.missionId,state.tries)
            }
            return {...initialState, apiKey:action.payload, missionTries:state.missionsTries, mission:state.mission}
        case UPDATE_IN_CHARGE_DETERMINISTIC:
            return { 
                ...initialState, 
                apiKey: state.apiKey, 
                isInCharge: action.payload.isInCharge, 
                mission:state.mission,
                tries: action.payload.tries,
                roomId: state.roomId
            }
        default:
            return state;
    }
  };
const initialState = {
    story:'',
    errorMessage: '',
    apiKey:'',
    currentAnswer:'',
    isInCharge:false,
    isInchargeByMission:false,
    isFinish:false,
    loading: false,
    roomId:'',
  };

  import {
    UPDATE_ERROR_SOLVE_STORY,
    CLEAR_STATE,
    CHANGE_IN_CHARGE,
    CURRENT_ANSWER_STORY_CHANGED,
    LOGIN_STUDENT,
    STORY_MISSION_SEND,
    INIT_STORY_MISSION,
    EXIT_ROOM,
    FINISH_MISSION,
    CHANGE_STORY_IN_CHARGE,
    FINISH_STORY_MISSION,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case INIT_STORY_MISSION:
            let roomData=action.payload.roomData;
            let missionData=roomData.currentMission;
            return {... state, roomId:roomData.roomId, loading: false, isInCharge:action.payload.isInCharge,
                isFinish:roomData.waitingForStory, story:missionData.story}
        case CURRENT_ANSWER_STORY_CHANGED:
            return { ...state,currentAnswer:action.payload};
        case LOGIN_STUDENT:
            return { ...initialState, apiKey: action.payload };
        case STORY_MISSION_SEND:
            return { ...state, loading: true };
        case CHANGE_IN_CHARGE:
            return { ...state, isInCharge: action.payload, isInchargeByMission:action.payload }; 
        case CHANGE_STORY_IN_CHARGE:
            return { ...state, loading: false, isInCharge: action.payload.isInCharge,
                story: state.isFinish ? state.story : action.payload.story, isInchargeByMission:false };
        case EXIT_ROOM:
            return {...initialState, apiKey:state.apiKey}
        case FINISH_MISSION:
            return {...initialState, apiKey:action.payload, isInCharge:state.isInchargeByMission, roomId:state.roomId}
        case FINISH_STORY_MISSION:
            return {... state, isInCharge:false, isFinish:true, story: action.payload}
        case UPDATE_ERROR_SOLVE_STORY:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };
const initialState = {
    roomId: '',
    missionId: '',
    hasFile: false,
    openText: "",
    question: '',
    apiKey:'',
  };

  import {
    INIT_OPEN_ANSWER
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case INIT_OPEN_ANSWER:
            return {...state, roomId: action.payload.roomId, missionId: action.payload.solutionData.missionId, hasFile: action.payload.solutionData.hasFile, openText: action.payload.solutionData.openAnswer, question: action.payload.solutionData.missionQuestion, apiKey: action.payload.apiKey}
        default:
            return state;
    }
  };
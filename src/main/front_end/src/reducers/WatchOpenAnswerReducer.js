const initialState = {
  roomId: '',
  missionId: '',
  hasFile: false,
  openText: "",
  question: '',
  apiKey: '',
  loading: false,
  errorMessage: "",
  fileName: ""
};

import {
  INIT_OPEN_ANSWER,
  RES_ANS_CLICKED,
  UPDATE_RES_ANS_ERROR,
  RES_ANS_RESET
} from '../actions/types';

export default (state = initialState, action) => {
  switch (action.type) {
    case INIT_OPEN_ANSWER:
      return { ...state, roomId: action.payload.roomId, missionId: action.payload.solutionData.missionId, hasFile: action.payload.solutionData.hasFile, openText: action.payload.solutionData.openAnswer, question: action.payload.solutionData.missionQuestion, apiKey: action.payload.apiKey, fileName: action.payload.solutionData.fileName }
    case RES_ANS_CLICKED:
      return {
        ...state,
        loading: true,
        errorMessage: '',
      };
    case UPDATE_RES_ANS_ERROR:
      alert(action.payload)
      return { ...state, errorMessage: action.payload, loading: false };
    case RES_ANS_RESET:
      return { ...initialState, apiKey: state.apiKey }
    default:
      return state;
  }
};
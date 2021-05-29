const initialState = {
  mission: undefined,
  errorMessage: "",
  apiKey: "",
  isInCharge: false,
  loading: false,
  roomId: "",
};

import {
  CHANGE_IN_CHARGE,
  CLEAR_STATE,
  FINISH_MISSION,
  INIT_TRIVIA_MISSION,
  LOGIN_STUDENT,
  SOLVE_TRIVIA_MISSION_SEND,
  UPDATE_ERROR_SOLVE_TRIVIA,
  EXIT_ROOM,
} from "../actions/types";

export default (state = initialState, action) => {
  switch (action.type) {
    case INIT_TRIVIA_MISSION:
      let { roomData, isInCharge } = action.payload;
      let roomId = roomData.roomId;
      let missionData = roomData.currentMission;
      return {
        ...state,
        roomId,
        loading: false,
        errorMessage: "",
        isInCharge,
        mission: missionData,
      };
    case LOGIN_STUDENT:
      return { ...initialState, apiKey: action.payload };
    case SOLVE_TRIVIA_MISSION_SEND:
      return { ...state, loading: true };
    case CHANGE_IN_CHARGE:
      return { ...state, isInCharge: action.payload };
    case UPDATE_ERROR_SOLVE_TRIVIA:
      return { ...state, errorMessage: action.payload, loading: false };
    case FINISH_MISSION:
      return { ...initialState, apiKey: action.payload };
    case EXIT_ROOM:
      return { ...initialState, apiKey: state.apiKey };
    case CLEAR_STATE:
      return initialState;
    default:
      return state;
  }
};

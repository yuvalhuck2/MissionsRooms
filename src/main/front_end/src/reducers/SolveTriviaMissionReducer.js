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
  EXIT_ROOM,
  FINISH_MISSION,
  INIT_TRIVIA_MISSION,
  LOGIN_STUDENT,
  SOLVE_TRIVIA_MISSION_SEND,
  UPDATE_ERROR_SOLVE_TRIVIA,
  FINISH_MISSION_TRIVIA,
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
      return {...initialState, apiKey: action.payload, mission: state.mission}
    case FINISH_MISSION_TRIVIA:
      return {
        ...initialState,
        apiKey: state.apiKey,
        mission: state.mission,
        isInCharge: action.payload,
        roomId: state.roomId,
      };
    case EXIT_ROOM:
      return { ...initialState, apiKey: state.apiKey };
    case CLEAR_STATE:
      return initialState;
    default:
      return { ...state };
  }
};

const initialState = {
    question: '',
    realAnswer: '',
    missionTypes: [],
    loading: false,
    errorMessage: '',
  };
  import {
    QUESTION_CHANGED,
    ANSWER_CHANGED,
    TYPES_CHANGED,
    ADD_MISSON,
    UPDATE_ERROR,
    CLEAR_STATE,
    DETERMINISTIC,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
      case DETERMINISTIC:
        //navigation
        console.log('move to detemistic')
        return state;
      case QUESTION_CHANGED:
        return { ...state, question: action.payload };
      case ANSWER_CHANGED:
        return { ...state, realAnswer: action.payload };
      case TYPES_CHANGED:
        return { ...state, missionTypes: action.payload };
      case ADD_MISSON:
        return { ...state, loading: true };
      case UPDATE_ERROR:
        console.log(`payload is:${action.payload}`)
        return { ...state, errorMessage: action.payload, loading: false };
      case CLEAR_STATE:
        return initialState;
      default:
        return state;
    }
  };
  
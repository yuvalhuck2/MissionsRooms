const initialState = {
    question: '',
    realAnswer: '',
    missionTypes: [],
    loading: false,
    errorMessage: '',
    apiKey:'',
    points:'',
    passRatio: '',
    triviaQuestions: [],
    allTriviaQuestions: [],
  };
  import {
    QUESTION_CHANGED,
    ANSWER_CHANGED,
    TYPES_CHANGED,
    ADD_MISSON,
    UPDATE_ERROR_MISSION,
    LOGIN_TEACHER,
    CLEAR_STATE,
    ADD_MISSION_POINTS_CHANGED,
    ENTER_ADD_MISSION,
    CHOOSE_TRIVIA_QUESTION,
    REMOVE_TRIVIA_QUESTION,
    PASS_RATIO_CHANGED,
    GOT_TRIVIA_QUESTIONS,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
      case QUESTION_CHANGED:
        return { ...state, question: action.payload };
      case ANSWER_CHANGED:
        return { ...state, realAnswer: action.payload };
      case TYPES_CHANGED:
        return { ...state, missionTypes: action.payload };
      case ADD_MISSION_POINTS_CHANGED:
        return { ...state, points: action.payload };
      case ADD_MISSON:
        return { ...state, loading: true };
      case ENTER_ADD_MISSION:
        return { ...initialState, apiKey: state.apiKey, points: state.points };
      case LOGIN_TEACHER:
        return { ...initialState, apiKey: action.payload };
      case CHOOSE_TRIVIA_QUESTION:
        return { ...state, triviaQuestions: [...state.triviaQuestions, action.payload]}
      case REMOVE_TRIVIA_QUESTION:
        let newTriviaQuestions = state.triviaQuestions.filter((item) => item.id != action.payload.id);
        return { ...state, triviaQuestions: newTriviaQuestions}
      case PASS_RATIO_CHANGED:
        return { ...state, passRatio: action.payload }
      case GOT_TRIVIA_QUESTIONS: 
        return { ...state, allTriviaQuestions: action.payload }
      case UPDATE_ERROR_MISSION:
        alert(action.payload)
        return { ...state, errorMessage: action.payload, loading: false };
      case CLEAR_STATE:
        return initialState;
      default:
        return state;
    }
  };
  
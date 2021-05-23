import {
  LOGIN_TEACHER,
  DELETE_QUESTION,
  DELETE_QUESTION_SUCCESS,
  DELETE_TRIVIA_QUESTION_CHANGED,
  GOT_TRIVIA_QUESTIONS,
  UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
  RESET_DELETE_TRIVIA_QUESTION,
} from "../actions/types";

const initialState = {
  apiKey: "",
  questions: [],
  selectedQuestion: "",
  errorMessage: "",
  loading: false,
};

export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_TEACHER:
      return { ...initialState, apiKey: action.payload };
    case DELETE_TRIVIA_QUESTION_CHANGED:
      return { ...state, selectedQuestion: action.payload };
    case GOT_TRIVIA_QUESTIONS:
      return { ...state, questions: action.payload };
    case DELETE_QUESTION:
      return { ...state, errorMessage: "", loading: true };
    case DELETE_QUESTION_SUCCESS:
      return { ...initialState, apiKey: state.apiKey, loading: false };
    case UPDATE_DELETE_TRIVIA_QUESTION_ERROR:
      return { ...state, errorMessage: action.payload, loading: false };
    case RESET_DELETE_TRIVIA_QUESTION:
      return { ...initialState, apiKey: state.apiKey }
    default:
      return state;
  }
};

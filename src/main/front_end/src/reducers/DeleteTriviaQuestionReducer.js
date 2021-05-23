import {
  DELETE_QUESTION,
  DELETE_QUESTION_SUCCESS,
  DELETE_TRIVIA_QUESTION_CHANGED,
  GOT_TRIVIA_QUESTIONS,
  UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
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
    case DELETE_TRIVIA_QUESTION_CHANGED:
      return { ...state, selectedQuestion: action.payload };
    case GOT_TRIVIA_QUESTIONS:
      return { ...state, questions: action.payload };
    case DELETE_QUESTION:
      return { ...state, errorMessage: "", loading: true };
    case DELETE_QUESTION_SUCCESS:
      return { ...initialState, apiKey: state.apiKey };
    case UPDATE_DELETE_TRIVIA_QUESTION_ERROR:
      return { ...state, errorMessage: action.payload };
    default:
      return state;
  }
};

import {
  ADD_TRIVIA_QUESTION,
  ADD_TRIVIA_QUESTION_SUCCESS,
  LOGIN_IT,
  LOGIN_TEACHER,
  TRIVIA_CORRECT_ANSWER_CHANGED,
  TRIVIA_QUESTION_CHANGED,
  TRIVIA_SUBJECTS_RETRIEVED,
  TRIVIA_SUBJECT_CHANGED_ADD_QUESTION,
  TRIVIA_WRONG_ANSWER_CHANGED,
  UPDATE_ERROR_ADD_TRIVIA_QUESTION,
} from '../actions/types';

const initialState = {
  apiKey: '',
  errorMessage: '',
  loading: false,
  question: '',
  wrongAnswers: ['', '', ''],
  correctAnswer: '',
  questionSubject: '',
  subjects: [],
};

const getNewWrongAnswers = ({ wrongAnswer, key }) => {
  let index = parseInt(key);
  let newWrongAnswers = [...initialState.wrongAnswers];
  newWrongAnswers[index] = wrongAnswer;
  return newWrongAnswers;
};

export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_IT:
      return { ...state, apiKey: action.payload };
    case LOGIN_TEACHER:
      return { ...state, apiKey: action.payload };
    case TRIVIA_SUBJECTS_RETRIEVED:
      return { ...state, subjects: action.payload };
    case TRIVIA_QUESTION_CHANGED:
      return { ...state, question: action.payload };
    case TRIVIA_SUBJECT_CHANGED_ADD_QUESTION:
      return { ...state, questionSubject: action.payload };
    case TRIVIA_CORRECT_ANSWER_CHANGED:
      return { ...state, correctAnswer: action.payload };
    case TRIVIA_WRONG_ANSWER_CHANGED:
      let newWrongAnswers = getNewWrongAnswers(action.payload);
      return { ...state, wrongAnswers: newWrongAnswers };
    case UPDATE_ERROR_ADD_TRIVIA_QUESTION:
      return { ...state, errorMessage: action.payload, loading: false };
    case ADD_TRIVIA_QUESTION:
      return { ...state, errorMessage: '', loading: false };
    case ADD_TRIVIA_QUESTION_SUCCESS:
      return {
        ...state,
        errorMessage: '',
        loading: false,
        question: '',
        wrongAnswers: ['', '', ''],
        correctAnswer: '',
        questionSubject: '',
        subjects: [],
      };
    default:
      return state;
  }
};

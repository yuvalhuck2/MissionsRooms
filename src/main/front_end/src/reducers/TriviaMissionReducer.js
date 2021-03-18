import {
  CHANGE_ANSWER_TRIVIA_MISSION,
  CLEAR_STATE,
  GET_TRIVIA_QUESTIONS,
  SUBMIT_ANSWERS_TRIVIA_MISSION,
  UPDATE_ERROR_TRIVIA_MISSION,
} from "../actions/types";

const initialState = {
  missionId: "",
  questions: new Map([
    [
      "id0",
      {
        questionId: "id0",
        question: "Question 0",
        correctAnswer: "Answer a",
        answers: ["Answer a", "Answer b", "Answer c", "Answer d"],
        currentAnswer: "Answer b",
      },
    ],
    [
      "id1",
      {
        questionId: "id1",
        question: "Question 1",
        correctAnswer: "Answer c",
        answers: ["Answer a", "Answer b", "Answer c", "Answer d"],
        currentAnswer: "Answer a",
      },
    ],
    [
      "id2",
      {
        questionId: "id2",
        question: "Question 2",
        correctAnswer: "Answer d",
        answers: ["Answer a", "Answer b", "Answer c", "Answer d"],
        currentAnswer: "Answer c",
      },
    ],
  ]),
  apiKey: "",
  loading: false,
  errorMessage: "",
  reload: false,
  isInCharge: true,
};

export default (state = initialState, action) => {
  switch (action.type) {
    case GET_TRIVIA_QUESTIONS:
      return { ...state, questions: action.payload };
    case CHANGE_ANSWER_TRIVIA_MISSION:
      let question = state.questions.get(action.payload.questionId);
      question.currentAnswer = action.payload.answer;
      return { ...state, reload: !state.reload };
    case SUBMIT_ANSWERS_TRIVIA_MISSION:
      return { ...state, loading: true };
    case UPDATE_ERROR_TRIVIA_MISSION:
      return { ...state, errorMessage: action.payload };
    case CLEAR_STATE:
      return initialState;
    default:
      return state;
  }
};

import {
  CHANGE_ANSWER_TRIVIA_MISSION,
  CLEAR_STATE,
  INIT_TRIVIA_MISSION,
  SUBMIT_ANSWERS_TRIVIA_MISSION,
  UPDATE_ERROR_TRIVIA_MISSION,
} from "../actions/types";

const initialState = {
  missionId: "",
  mission: undefined,
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
    case INIT_TRIVIA_MISSION:
      return {
        ...state,
        questions: action.payload.triviaMap,
        mission: action.payload.currentMission,
        isInCharge: action.payload.isInCharge,
      };
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

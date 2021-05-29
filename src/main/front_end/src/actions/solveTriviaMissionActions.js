import API from "../api/API";
import * as APIPaths from "../api/APIPaths";
import { GeneralErrors, SolveTriviaMissionStrings } from "../locale/locale_heb";
import { Success } from "./OpCodeTypes";
import { SOLVE_TRIVIA_MISSION_SEND, UPDATE_ERROR_SOLVE_TRIVIA } from "./types";

const { server_error } = GeneralErrors;
const { triviaPassMessage, triviaFailMessage } = SolveTriviaMissionStrings;

const calculateNumberOfRightAnswers = (correctAnswers, currentAnswers) => {
  let count = 0;
  for (let i = 0; i < correctAnswers.length; i++) {
    if (correctAnswers[i] === currentAnswers[i]) {
      count++;
    }
  }
  return count;
};

const checkIfSolvedTriviaMission = (mission, currentAnswers) => {
  let triviaQuestions = Object.values(mission.triviaQuestionMap);
  let correctAnswers = triviaQuestions.map((q) => q.correctAnswer);
  let amountOfQuestions = correctAnswers.length;
  let numRights = calculateNumberOfRightAnswers(correctAnswers, currentAnswers);
  let actualRatio = numRights / amountOfQuestions;
  let passed = actualRatio >= mission.passRatio;
  return { passed, numRights };
};

export const sendTriviaForm = ({
  roomId,
  mission,
  apiKey,
  navigation,
  currentAnswers,
}) => {
  return async (dispatch) => {
    const { passed, numRights } = checkIfSolvedTriviaMission(
      mission,
      currentAnswers
    );
    try {
      dispatch({ type: SOLVE_TRIVIA_MISSION_SEND });
      const solution = { roomId, answer: passed, apiKey };
      const res = await API.post(APIPaths.solveTrivia, solution);
      res
        ? checkSolveTriviaResponse(res.data, dispatch, navigation, passed)
        : dispatch({
            type: UPDATE_ERROR_SOLVE_TRIVIA,
            payload: server_error,
          });
    } catch (err) {
      return dispatch({
        type: UPDATE_ERROR_SOLVE_TRIVIA,
        payload: server_error,
      });
    }
  };
};

export const checkSolveTriviaResponse = (
  data,
  dispatch,
  navigation,
  passed
) => {
  const { reason } = data;
  switch (reason) {
    case Success:
      let solutionMessage = passed ? triviaPassMessage : triviaFailMessage;
      alert(solutionMessage);
      return dispatch({ type: "" });
    default:
      return dispatch({
        type: UPDATE_ERROR_SOLVE_TRIVIA,
        payload: server_error,
      });
  }
};

import API from "../api/API";
import * as APIPaths from "../api/APIPaths";
import { AddTriviaQuestionStrings } from "../locale/locale_heb";
import {
  Invalid_Trivia_Question,
  Success,
  Trivia_Subject_Doesnt_exist,
} from "./OpCodeTypes";
import {
  ADD_TRIVIA_QUESTION,
  ADD_TRIVIA_QUESTION_SUCCESS,
  RESET_ADD_TRIVIA_QUESTION,
  TRIVIA_CORRECT_ANSWER_CHANGED,
  TRIVIA_QUESTION_CHANGED,
  TRIVIA_SUBJECTS_RETRIEVED,
  TRIVIA_SUBJECT_CHANGED_ADD_QUESTION,
  TRIVIA_WRONG_ANSWER_CHANGED,
  UPDATE_ERROR_ADD_TRIVIA_QUESTION,
} from "./types";

const {
  invalid_trivia_question_error,
  choose_trivia_subject_error,
  trivia_question_added,
  selectTriviaSubjectError,
  emptyAnswersError,
  emptyQuestionError,
  uniqueAnswersError,
} = AddTriviaQuestionStrings;

export const questionChanged = (question) => {
  return {
    type: TRIVIA_QUESTION_CHANGED,
    payload: question,
  };
};

export const wrongAnswerChanged = (wrongAnswer, key) => {
  return {
    type: TRIVIA_WRONG_ANSWER_CHANGED,
    payload: {
      wrongAnswer,
      key,
    },
  };
};

export const correctAnswerChanged = (correctAnswer) => {
  return {
    type: TRIVIA_CORRECT_ANSWER_CHANGED,
    payload: correctAnswer,
  };
};

export const subjectChanged = (subject) => {
  return {
    type: TRIVIA_SUBJECT_CHANGED_ADD_QUESTION,
    payload: subject,
  };
};

export const getCurrentSubjects = ({ apiKey }) => {
  return async (dispatch) => {
    try {
      const res = await API.post(
        `${APIPaths.getTriviaSubjects}?apiKey=${apiKey}`
      );
      res
        ? checkGetCurrentSubjectsResponse(res.data, dispatch)
        : dispatch({ type: "" });
    } catch (err) {
      return dispatch({ type: "" });
    }
  };
};

const checkGetCurrentSubjectsResponse = (data, dispatch) => {
  const { reason, value } = data;
  let subjects = value.map((obj) => obj.subject);
  switch (reason) {
    case Success:
      return dispatch({ type: TRIVIA_SUBJECTS_RETRIEVED, payload: subjects });
    default:
      return dispatch({ type: "" });
  }
};

const validateTriviaQuestion = (triviaQuestionData, dispatch) => {
  const { wrongAnswers, correctAnswer, subject, question } = triviaQuestionData;
  const answers = [...wrongAnswers, correctAnswer];

  if (subject === undefined || subject === "") {
    dispatch({
      type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
      payload: selectTriviaSubjectError,
    });
    return false;
  }

  if (answers.some((answer) => answer === undefined || answer === "")) {
    dispatch({ type: UPDATE_ERROR_ADD_TRIVIA_QUESTION, payload: emptyAnswersError });
    return false;
  }

  if (question === undefined || question === "") {
    dispatch({ type: UPDATE_ERROR_ADD_TRIVIA_QUESTION, payload: emptyQuestionError });
    return false;
  }
  let answersSet = answers.filter((x, i, a) => a.indexOf(x) === i);
  if (answersSet.length !== answers.length) {
    dispatch({ type: UPDATE_ERROR_ADD_TRIVIA_QUESTION, payload: uniqueAnswersError });
    return false;
  }
  return true;
};

export const addTriviaQuestion = ({
  apiKey,
  question,
  wrongAnswers,
  correctAnswer,
  subject,
  navigation,
}) => {
  return async (dispatch) => {
    try {
      const triviaQuestionData = {
        id: "",
        question,
        wrongAnswers,
        correctAnswer,
        subject,
      };
      dispatch({ type: ADD_TRIVIA_QUESTION });
      if (validateTriviaQuestion(triviaQuestionData, dispatch)) {
        const res = await API.post(
          `${APIPaths.addTriviaQuestion}?apiKey=${apiKey}`,
          triviaQuestionData
        );
        res
          ? checkAddTriviaQuestionResponse(res.data, dispatch, navigation)
          : dispatch({
              type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
              payload: server_error,
            });
      }
    } catch (err) {
      return dispatch({
        type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
        payload: server_error,
      });
    }
  };
};

const checkAddTriviaQuestionResponse = (data, dispatch, navigation) => {
  const { reason } = data;

  switch (reason) {
    case Success:
      alert(trivia_question_added);
      navigation.goBack();
      return dispatch({ type: ADD_TRIVIA_QUESTION_SUCCESS });
    case Invalid_Trivia_Question:
      return dispatch({
        type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
        payload: invalid_trivia_question_error,
      });
    case Trivia_Subject_Doesnt_exist:
      return dispatch({
        type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
        payload: choose_trivia_subject_error,
      });
    default:
      return dispatch({
        type: UPDATE_ERROR_ADD_TRIVIA_QUESTION,
        payload: server_error,
      });
  }
};

export const handleBack = ({ navigation, apiKey }) => {
  navigation.goBack();
  return {
    type: RESET_ADD_TRIVIA_QUESTION,
    payload: apiKey,
  };
};

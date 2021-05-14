import API from '../api/API';
import {
  TRIVIA_CORRECT_ANSWER_CHANGED,
  TRIVIA_QUESTION_CHANGED,
  TRIVIA_SUBJECT_CHANGED,
  TRIVIA_WRONG_ANSWER_CHANGED,
} from './types';

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
        type: TRIVIA_SUBJECT_CHANGED
    }
}

export const addQuestion = ({ apiKey, question }) => {
  return async (dispatch) => {
    try {
      const res = await API.post(
        `${APIPaths.addTriviaSubject}?apiKey=${apiKey}`,
        { question }
      );
      res
        ? checkAddTriviaQuestionResponse(res.data, dispatch)
        : dispatch({ type: ADD_TRIVIA_QUESTION_ERROR, payload: server_error });
    } catch (err) {
      return dispatch({
        type: ADD_TRIVIA_QUESTION_ERROR,
        payload: server_error,
      });
    }
  };
};

const checkAddTriviaQuestionResponse = (data, dispatch) => {};

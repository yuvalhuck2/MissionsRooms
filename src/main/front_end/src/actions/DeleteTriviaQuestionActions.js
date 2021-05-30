import * as APIPaths from '../api/APIPaths';
import API from "../api/API";
import {
  deleteTriviaQuestionStrings,
  GeneralErrors,
} from '../locale/locale_heb';
import { Invalid_Trivia_Question, Exist_In_Mission, Success } from './OpCodeTypes';
import {
  DELETE_QUESTION,
  DELETE_QUESTION_SUCCESS,
  UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
  DELETE_TRIVIA_QUESTION_CHANGED,
  RESET_DELETE_TRIVIA_QUESTION
} from './types';

const { server_error } = GeneralErrors;

const {
  invalid_trivia_question_error,
  question_deleted,
  question_in_mission,
} = deleteTriviaQuestionStrings;

export const questionChanged = (question) => {
  return {
    type: DELETE_TRIVIA_QUESTION_CHANGED,
    payload: question,
  };
};

export const deleteTriviaQuestion = ({ apiKey, questionStr, navigation }) => {
  return async (dispatch) => {
    try {
      dispatch({ type: DELETE_QUESTION });
      const res = await API.post(
        `${APIPaths.deleteTriviaQuestion}?apiKey=${apiKey}`,
        questionStr
      );
      res
        ? checkGetDeleteQuestionResponse(res.data, dispatch, navigation)
        : dispatch({
            type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
            payload: server_error,
          });
    } catch (err) {
      return dispatch({
        type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
        payload: server_error,
      });
    }
  };
};

const checkGetDeleteQuestionResponse = (data, dispatch, navigation) => {
  const { reason } = data;
  switch (reason) {
    case Success:
      navigation.goBack();
      alert(question_deleted);
      return dispatch({ type: DELETE_QUESTION_SUCCESS });
    case Invalid_Trivia_Question:
      return dispatch({
        type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
        payload: invalid_trivia_question_error,
      });
    case Exist_In_Mission:
      return dispatch({
        type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
        payload: question_in_mission,
      })
    default:
      return dispatch({
        type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR,
        payload: server_error,
      });
  }
};

export const handleBack = ({navigation}) => {
  navigation.goBack();
  return {
      type: RESET_DELETE_TRIVIA_QUESTION
  }
}

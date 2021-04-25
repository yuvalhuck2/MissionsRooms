import {
  ADD_MISSON,
  ANSWER_CHANGED,
  DETERMINISTIC,
  LOGIN_TEACHER,
  OPEN_QUESTION,
  QUESTION_CHANGED,
  TYPES_CHANGED,
  UPDATE_ERROR_MISSION,
  ADD_MISSION_POINTS_CHANGED,
  ENTER_ADD_MISSION,
} from '../actions/types';
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import {
  AddDeterministicMissionErrors,
  AddDeterministicMissionSuccess,
  addMissionErrors,
  GeneralErrors,
} from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import {
  Not_Exist,
  Not_Mission,
  Null_Error,
  Success,
  Wrong_Type,
  Wrong_Key,
} from './OpCodeTypes';

const {
  question_empty,
  answer_empty,
  types_empty,
  points_must_be_positive,
} = AddDeterministicMissionErrors;

const { mission_added } = AddDeterministicMissionSuccess;

const {
  server_error,
  wrong_key_error,
  teacher_not_exists_error,
} = GeneralErrors;

const { mission_details_error } = addMissionErrors;

export const questionChanged = (text) => {
  return {
    type: QUESTION_CHANGED,
    payload: text,
  };
};

export const answerChanged = (text) => {
  return {
    type: ANSWER_CHANGED,
    payload: text,
  };
};

export const typesChanged = (list) => {
  return {
    type: TYPES_CHANGED,
    payload: list,
  };
};

export const navigateToMission = (type, navigation, points) => {
  return async (dispatch) => {
    if(points.trim()=="" || parseInt(points) <= 0){
      return dispatch({type: UPDATE_ERROR_MISSION, payload: points_must_be_positive})
    }
    dispatch({type: ENTER_ADD_MISSION})

    switch (type) {
      case DETERMINISTIC:
        return navigation.navigate(NavPaths.AddDeterministicMission);
      case OPEN_QUESTION:
        return navigation.navigate(NavPaths.AddOpenQuestionMission);

    }
  };
};

export const addMission = ({
  apiKey,
  navigation,
  missionTypes,
  className,
  points,
  question = undefined,
  realAnswer = undefined,
}) => {
  return async (dispatch) => {
    if(points.trim()=="" || parseInt(points) <= 0){
      return dispatch({type: UPDATE_ERROR_MISSION, payload: points_must_be_positive})
    }
    if (question != undefined && question.trim() === '') {
      dispatch({ type: UPDATE_ERROR_MISSION, payload: question_empty });
    } else if ( realAnswer != undefined && realAnswer.trim() === '') {
      dispatch({ type: UPDATE_ERROR_MISSION, payload: answer_empty });
    } else if (missionTypes.length == 0) {
      dispatch({ type: UPDATE_ERROR_MISSION, payload: types_empty });
    } else {
      try {
        dispatch({ type: ADD_MISSON });
        missionData = JSON.stringify({
          CLASSNAME: className,
          DATA: { question, realAnswer, missionTypes, points },
        });
        const res = await API.post(APIPaths.addMission, {
          apiKey,
          missionData,
        });
        res
          ? checkAddMissionResponse(res.data, dispatch, navigation, apiKey)
          : dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
      } catch (err) {
        console.log(err);
        dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
      }
    }
  };
};


export const addOpenMission = ({
  apiKey,
  question,
  missionTypes,
  navigation,
}) => {
  return async (dispatch) => {
    if (question.trim() === '') {
      dispatch({ type: UPDATE_ERROR_MISSION, payload: question_empty });
    } else if (missionTypes.length == 0) {
      dispatch({ type: UPDATE_ERROR_MISSION, payload: types_empty });
    } else {
      try {
        dispatch({ type: ADD_MISSON });
        missionData = JSON.stringify({
          CLASSNAME: OPEN_QUESTION,
          DATA: { question, missionTypes },
        });
        const res = await API.post(APIPaths.addMission, {
          apiKey,
          missionData,
        });
        res
          ? checkAddMissionResponse(res.data, dispatch, navigation, apiKey)
          : dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
      } catch (err) {
        console.log(err);
        dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
      }
    }
  };
};

const checkAddMissionResponse = (data, dispatch, navigation, apiKey) => {
  const { reason } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_MISSION, payload: wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_MISSION,
        payload: teacher_not_exists_error,
      });
    case Null_Error:
      return dispatch({
        type: UPDATE_ERROR_MISSION,
        payload: mission_details_error,
      });
    case Wrong_Type:
      return dispatch({
        type: UPDATE_ERROR_MISSION,
        payload: mission_details_error,
      });
    case Not_Mission:
      return dispatch({
        type: UPDATE_ERROR_MISSION,
        payload: mission_details_error,
      });
    case Success:
      navigation.navigate(NavPaths.teacherMainScreen);
      alert(mission_added);
      return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    default:
      return dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
  }
};

export const changePoints = (points) => {
  return {
    type: ADD_MISSION_POINTS_CHANGED,
    payload: points,
  };
};

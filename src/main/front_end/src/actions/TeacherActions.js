import {
  CLEAR_STATE,
  GET_CLASSROOM,
  GET_MISSIONS,
  GET_TEMPLATES,
  LOGIN_TEACHER,
  UPDATE_ERROR_ROOM,
  UPDATE_ERROR_TEMPLATE,
} from '../actions/types';
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import { Not_Exist, Success, Wrong_Key } from './OpCodeTypes';
import {closeSocket} from '../handler/WebSocketHandler'

const {
  server_error,
  wrong_key_error,
  teacher_not_exists_error,
} = GeneralErrors;

export const passToAddTemplate = ({ navigation, apiKey }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    navigation.navigate(NavPaths.addTemplate);
    try {
      const res = await API.post(APIPaths.searchMission, { apiKey });
      res
        ? checkSearchMissionResponse(res.data, dispatch)
        : dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
    }
  };
};

const checkSearchMissionResponse = (data, dispatch) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      return dispatch({ type: GET_MISSIONS, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
  }
};

export const passToAddRoom = ({ navigation, apiKey }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    try {
      const res = await API.post(APIPaths.getClassroom, { apiKey });
      if (res) {
        checkGetClassroomResponse(res.data, dispatch, navigation);
      } else {
        dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
    try {
      const res = await API.post(APIPaths.searchTemplate, { apiKey });
      res
        ? checkSearchTemplatesResponse(res.data, dispatch)
        : dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
  };
};

const checkGetClassroomResponse = (data, dispatch, navigation) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      dispatch({ type: GET_CLASSROOM, payload: value });
      return navigation.navigate(NavPaths.AddRoom);
    default:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
  }
};

const checkSearchTemplatesResponse = (data, dispatch) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      return dispatch({ type: GET_TEMPLATES, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
  }
};

export const logout = (navigation) => {
  return async (dispatch) => {
    dispatch({ type: CLEAR_STATE });
    closeSocket();
    return navigation.navigate(NavPaths.loginScreen);
  };
};

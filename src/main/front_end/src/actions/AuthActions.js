import API from '../api/API';
import { authErrors } from '../locale/locale_heb';
import * as APIPaths from '../api/APIPaths'
import * as NavPaths from '../navigation/NavPaths'
import {
  Already_Exist,
  DB_Error,
  IT,
  Mail_Error,
  Not_Exist,
  Student,
  Supervisor,
  Teacher,
  TimeOut,
  Wrong_Alias,
  Wrong_Password,
} from './OpCodeTypes';
import {
  EMAIL_CHANGED,
  LOGIN_IT,
  LOGIN_STUDENT,
  LOGIN_SUPERVISOR,
  LOGIN_TEACHER,
  PASSWORD_CHANGED,
  REGISTER_STUDENT,
  REGISTER_TEACHER,
  REGISTER_USER,
  UPDATE_ERROR,
} from './types';

const {
  wrong_password_login,
  wrong_password,
  wrong_alias,
  server_error,
  not_exist,
  already_exist,
} = authErrors;

// THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// console.log(uri);

export const emailChanged = (text) => {
  return {
    type: EMAIL_CHANGED,
    payload: text,
  };
};

export const passwordChanged = (text) => {
  return {
    type: PASSWORD_CHANGED,
    payload: text,
  };
};

export const registerUser = ({ email, password, navigation }) => {
  return async (dispatch) => {
    dispatch({ type: REGISTER_USER });
    const res = await API.post(APIPaths.register, { alias: email, password });
    console.log(res.data);
    res
      ? checkRegisterUserResponse(res.data, dispatch, navigation)
      : dispatch({ type: UPDATE_ERROR, payload: server_error });
  };
};

const checkRegisterUserResponse = (data, dispatch, navigation) => {
  const { reason, value } = data;
  console.log(reason);

  switch (reason) {
    case Wrong_Password:
      console.log(wrong_password);
      return dispatch({ type: UPDATE_ERROR, payload: wrong_password });
    case Wrong_Alias:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_alias });
    case TimeOut:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
    case DB_Error:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
    case Not_Exist:
      return dispatch({ type: UPDATE_ERROR, payload: not_exist });
    case Already_Exist:
      return dispatch({ type: UPDATE_ERROR, payload: already_exist });
    case Mail_Error:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
    case Teacher:
      // TODO: navigate to add activation code when it is done
      navigation.navigate(NavPaths.login);
      return dispatch({ type: REGISTER_TEACHER });
    case Student:
      navigation.navigate(NavPaths.login);
      return dispatch({ type: REGISTER_STUDENT, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

export const loginUser = ({ email, password, navigation }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_USER });
    const res = await API.post(APIPaths.login, { alias: email, password });
    res
      ? checkLoginUserResponse(res.data, dispatch, navigation)
      : dispatch({ type: UPDATE_ERROR, payload: server_error });
  };
};

const checkLoginUserResponse = (data, dispatch, navigation) => {
  const { reason, value } = data;

  switch (reason) {
    case Wrong_Password:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_password_login });
    case Wrong_Alias:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_alias });
    case Not_Exist:
      return dispatch({ type: UPDATE_ERROR, payload: not_exist });
    case Supervisor:
      // TODO: navigate to main screen supervisor
      navigation.navigate(NavPaths.supMainScreen);
      return dispatch({ type: LOGIN_SUPERVISOR, payload: value });
    case IT:
      // TODO: navigate to IT main screen
      navigation.navigate(NavPaths.ITMainScreen);
      return dispatch({ type: LOGIN_IT, payload: value });
    case Teacher:
      // TODO: navigate to Teacher main screen
      navigate.navigate(NavPaths.teacherMainScreen);
      return dispatch({ type: LOGIN_TEACHER, payload: value });
    case Student:
      // TODO: navigate to Student main screen
      navigate.navigate(NavPaths.studentMainScreen);
      return dispatch({ type: LOGIN_STUDENT, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

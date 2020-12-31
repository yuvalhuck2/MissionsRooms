import API from '../api/API';
import { registerErrors } from '../locale/locale_heb';
import {
  Already_Exist,
  DB_Error,
  Mail_Error,
  Not_Exist,
  Student,
  Teacher,
  TimeOut,
  Wrong_Alias,
  Wrong_Password,
} from './OpCodeTypes';
import {
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  REGISTER_USER,
  UPDATE_ERROR,
  REGISTER_TEACHER,
  REGISTER_STUDENT,
} from './types';

const {
  wrong_password,
  wrong_alias,
  server_error,
  not_exist,
  already_exist,
} = registerErrors;

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

export const registerUser = ({ email, password }) => {
  return async (dispatch) => {
    dispatch({ type: REGISTER_USER });
    const res = await API.post('/UserAuth', { alias: email, password });
    console.log(res.data);
    if (res) {
      checkRegisterUserResponse(res.data, dispatch);
    }
  };
};

const checkRegisterUserResponse = (data, dispatch) => {
  const { reason, value } = data;
  console.log(reason);

  switch (reason) {
    case Wrong_Password:
      console.log(wrong_password)
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
      return dispatch({ type: REGISTER_TEACHER});
    case Student:
      return dispatch({ type: REGISTER_STUDENT, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

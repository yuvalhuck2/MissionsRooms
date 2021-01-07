import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { authErrors, registerCodeErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
//import Constants from 'expo-constants'
import {
  Already_Exist,
  Code_Not_Match,
  DB_Error,
  IT,
  Mail_Error,
  Not_Exist,
  Not_Registered,
  Student,
  Success,
  Supervisor,
  Teacher,
  TimeOut,
  Wrong_Alias,
  Wrong_Password,
  Wrong_Type,
  Wrong_Code,
  Not_Exist_Group,
  Already_Exist_Student,
} from './OpCodeTypes';
import {
  CLEAR_STATE,
  CODE_CHANGED,
  EMAIL_CHANGED,
  LOGIN_IT,
  LOGIN_STUDENT,
  LOGIN_SUPERVISOR,
  LOGIN_TEACHER,
  LOGIN_USER,
  PASSWORD_CHANGED,
  REGISTER_CODE,
  REGISTER_CODE_SUCCESS,
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

const {
  wrong_alias_register_code,
  wrong_register_code,
  not_registered_register_code,
  code_not_match_register_code,
  wrong_type_register_code,
  already_exist_register_code,
  not_exist_group_register_code,
  already_exist_student_register_code,
} = registerCodeErrors;

// THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// alert(uri);

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

export const codeChanged = (code) => {
  return {
    type: CODE_CHANGED,
    payload: code,
  };
};

export const registerUser = ({ email, password, navigation }) => {
  return async (dispatch) => {
    dispatch({ type: REGISTER_USER });
    try {
      const res = await API.post(APIPaths.register, { alias: email, password });
      res
        ? checkRegisterUserResponse(res.data, dispatch, navigation)
        : dispatch({ type: UPDATE_ERROR, payload: server_error });
    } catch (err) {
      console.log(err);
      dispatch({ type: UPDATE_ERROR, payload: server_error });
    }
  };
};

const checkRegisterUserResponse = (data, dispatch, navigation) => {
  const { reason, value } = data;

  switch (reason) {
    case Wrong_Password:
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
      if (navigation) navigation.navigate(NavPaths.authCodeScreen);
      return dispatch({ type: REGISTER_TEACHER });
    case Student:
      if (navigation) navigation.navigate(NavPaths.authCodeScreen);
      return dispatch({ type: REGISTER_STUDENT, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

export const registerCode = ({
  alias,
  code,
  teacherAlias,
  groupType,
  navigation,
}) => {
  return async (dispatch) => {
    dispatch({ type: REGISTER_CODE });
    try {
      const res = await API.post(APIPaths.registerCode, {
        alias,
        code,
        teacherAlias,
        groupType,
      });
      res
        ? checkRegisterCodeResponse(res.data, dispatch, navigation)
        : dispatch({ type: UPDATE_ERROR, payload: server_error });
    } catch (err) {
      console.log(err);
      dispatch({ type: UPDATE_ERROR, payload: server_error });
    }
  };
};

const checkRegisterCodeResponse = (data, dispatch, navigation) => {
  const { reason } = data;

  switch (reason) {
    case Wrong_Alias:
      return dispatch({
        type: UPDATE_ERROR,
        payload: wrong_alias_register_code,
      });
    case Wrong_Code:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_register_code });
    case Not_Registered:
      return dispatch({
        type: UPDATE_ERROR,
        payload: not_registered_register_code,
      });
    case Code_Not_Match:
      return dispatch({
        type: UPDATE_ERROR,
        payload: code_not_match_register_code,
      });
    case Wrong_Type:
      return dispatch({
        type: UPDATE_ERROR,
        payload: wrong_type_register_code,
      });
    case Not_Exist:
      return dispatch({ type: UPDATE_ERROR, payload: not_exist_register_code });
    case Already_Exist:
      return dispatch({
        type: UPDATE_ERROR,
        payload: already_exist_register_code,
      });
    case Not_Exist_Group:
      return dispatch({
        type: UPDATE_ERROR,
        payload: not_exist_group_register_code,
      });
    case Already_Exist_Student:
      return dispatch({
        type: UPDATE_ERROR,
        payload: already_exist_student_register_code,
      });
    case Success:
      navigation.navigate(NavPaths.loginScreen);
      return dispatch({ type: REGISTER_CODE_SUCCESS });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

export const loginUser = ({ email, password, navigation }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_USER });
    try {
      const res = await API.post(APIPaths.login, { alias: email, password });
      res
        ? checkLoginUserResponse(res.data, dispatch, navigation)
        : dispatch({ type: UPDATE_ERROR, payload: server_error });
    } catch (err) {
      console.log(err);
      dispatch({ type: UPDATE_ERROR, payload: server_error });
    }
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
      navigation.navigate(NavPaths.supMainScreen);
      return dispatch({ type: LOGIN_SUPERVISOR, payload: value });
    case IT:
      // TODO: navigate to IT main screen
      navigation.navigate(NavPaths.ITMainScreen);
      return dispatch({ type: LOGIN_IT, payload: value });
    case Teacher:
      navigation.navigate(NavPaths.teacherMainScreen);
      return dispatch({ type: LOGIN_TEACHER, payload: value });
    case Student:
      navigation.navigate(NavPaths.studentMainScreen);
      return dispatch({ type: LOGIN_STUDENT, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

export const navigateToRegister = ({ navigation }) => {
  navigation.navigate(NavPaths.registerScreen);
  return { type: CLEAR_STATE };
};

export const navigateToLogin = ({ navigation }) => {
  navigation.navigate(NavPaths.loginScreen);
  return { type: CLEAR_STATE };
};

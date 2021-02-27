import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors,addITErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';

import {
    ALIAS_IT_CHANGED,
    ADD_IT,
    PASSWORD_IT__CHANGED,
    UPDATE_ADD_IT_ERROR,
    ADD_IT_RESET,
} from './types';

import {
    Wrong_Password,
    Wrong_Key,
    Wrong_Alias,
    DB_Error,
    Not_Exist,
    Already_Exist,
    Success,
} from './OpCodeTypes'

const {
    server_error,
    wrong_key_error,
} = GeneralErrors;

const {
    wrong_password,
    wrong_alias,
    not_exist,
    already_exist,
    added_successfully,
} = addITErrors;




export const aliasChanged = (text) => {
    return {
      type: ALIAS_IT_CHANGED,
      payload: text,
    };
  };
  
  export const passwordChanged = (text) => {
    return {
      type: PASSWORD_IT__CHANGED,
      payload: text,
    };
  };

  export const navigateToITScreen = ({ navigation,apiKey }) => {
    navigation.navigate(NavPaths.ITMainScreen);
    return { type: ADD_IT_RESET, payload:apiKey };
  };

  export const addIT = ({ alias, password,apiKey }) => {
    return async (dispatch) => {
        if (alias.trim() === '') {
            return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: wrong_alias });
          } if (password.trim() === '') {
            return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: wrong_password });
          }
        dispatch({ type: ADD_IT });
        try {
          const res = await API.post(APIPaths.addNewIT, { apiKey, alias, password });
          res
            ? checkAddNewITResponse(res.data, dispatch)
            : dispatch({ type: UPDATE_ADD_IT_ERROR, payload: server_error });
        } catch (err) {
          console.log(err);
          dispatch({ type: UPDATE_ADD_IT_ERROR, payload: server_error });
        }
      };
  }

  const checkAddNewITResponse = (data, dispatch) => {
    const { reason, value } = data;
  
    switch (reason) {
      case Wrong_Key:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: wrong_key_error });
      case Wrong_Password:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: wrong_password });
      case Wrong_Alias:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: wrong_alias });
      case DB_Error:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: server_error });
      case Not_Exist:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: not_exist });
      case Already_Exist:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: already_exist });
      case Success:
        alert(added_successfully)
        return dispatch({ type: ADD_IT_RESET});
      default:
        return dispatch({ type: UPDATE_ADD_IT_ERROR, payload: server_error });
    }
  };
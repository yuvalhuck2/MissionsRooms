import { GeneralErrors, ResetPasswordStrings } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';

import {
    ALIAS_CHANGED_RESET_PASSWORD,
    RESET_PASSWORD_FINISHED,
    RESET_PASSWORD_WAIT,
    UPDATE_ERROR_RESET_PASSWORD,
  } from '../actions/types';

  import {
    Success,
    Wrong_Key,
    Not_Exist,
    Mail_Error,
  } from './OpCodeTypes';


  const {
    server_error,
    wrong_key_error,
    wrong_alias,
    mail_error,
  } = GeneralErrors;

  const {
    reset_password_succeeded,
  } = ResetPasswordStrings


export const changeAlias = (alias) => {
    return {
        type: ALIAS_CHANGED_RESET_PASSWORD,
        payload: alias,
    };
}

export const resetPassword = ({alias, navigation}) => {
    return async (dispatch)=>{
        if(alias.trim() === ""){
            return dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: wrong_alias });
        }
        try {
            dispatch({type: RESET_PASSWORD_WAIT})
            const request = {alias};
            const res = await API.post(APIPaths.resetPassword,request);
            res
                ? checkResetPasswordResponse(res.data, dispatch, navigation)
                : dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: server_error });
        }
    } 
}

const checkResetPasswordResponse = (data, dispatch, navigation) => {
    const {reason} = data
    switch (reason) {
      case Mail_Error:
        return dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: mail_error }); 
      case Wrong_Key: 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: wrong_key_error });
      case Success:
        alert(reset_password_succeeded)
        navigation.navigate(NavPaths.loginScreen)
        return dispatch({ type: RESET_PASSWORD_FINISHED});
      default:
        return dispatch({ type: UPDATE_ERROR_RESET_PASSWORD, payload: server_error });
    }
}
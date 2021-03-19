import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { authErrors, ChangePasswordStrings ,GeneralErrors } from '../locale/locale_heb';
import { 
    NEW_PASSWORD_CHANGED,
    RESET_CHANGE_PASSWORD,
    UPDATE_ERROR_CHANGE_PASSWORD,
    WAIT_FOR_CHANGE_PASSWORD
} from '../actions/types';

import {
    Success,
    Not_Exist,
    Wrong_Password,
  } from './OpCodeTypes';

const {
    server_error,
    user_not_exist,
} = GeneralErrors;

const {
    wrong_password
} = authErrors

const {
    password_changed
} = ChangePasswordStrings

export const passwordChanged = (password) => {
    return {
        type: NEW_PASSWORD_CHANGED,
        payload: password,
    }
} 

export const changePassword = ({apiKey, password}) => {
    return async (dispatch)=>{
        if(password.trim() === ''){
            return dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: wrong_password})
        }
        try {
            dispatch({type: WAIT_FOR_CHANGE_PASSWORD})
            const request = {apiKey, password};
            const res = await API.post(APIPaths.changePassword,request);
            res
                ? checkChangePasswordResponse(res.data, dispatch,apiKey)
                : dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: server_error });
        }
    } 
}

const checkChangePasswordResponse = (data, dispatch,apiKey) => {
    const {reason} = data
    switch (reason) {
      case Wrong_Password:
        return dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: wrong_password });
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: user_not_exist });
      case Success:
        alert(password_changed)
        return dispatch({ type: RESET_CHANGE_PASSWORD, payload:apiKey});
      default:
        return dispatch({ type: UPDATE_ERROR_CHANGE_PASSWORD, payload: server_error });
    }
}

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_CHANGE_PASSWORD,
        payload: apiKey
    }
}
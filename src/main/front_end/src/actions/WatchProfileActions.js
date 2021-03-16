import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import { ProfileErrors ,GeneralErrors } from '../locale/locale_heb';

import { SEARCH_CHANGED,
         USERS_CHANGED,
         RESET_WATCH_PROFILE,
         PROFILE_CHANGED,
         MESSAGE_CHANGED,
         UPDATE_ERROR_WATCH_PROFILE,
         WAIT_FOR_MESSAGE,
         MESSAGE_SENT,
} from "./types";

import {
    Success,
    Not_Exist,
    Empty,
  } from './OpCodeTypes';

const {
    user_not_exist,
    server_error,
} = GeneralErrors;

const {
    message_empty,
    message_sent,
} = ProfileErrors;

export const searchChanged= (text) => {
    return {
        type: SEARCH_CHANGED,
        payload: text,
    };
} ;

export const filterUsers = (search, users) =>{
    return {
        type: USERS_CHANGED,
        payload: users.filter((user)=> user.alias.includes(search))
    }
};

export const changeDialog = (alias) =>{
    return {
        type: PROFILE_CHANGED,
        payload: alias,
    }
};

export const messageChanged = (text) => {
    return {
        type: MESSAGE_CHANGED,
        payload: text,
    }
} ;

export const sendMessage = ({apiKey, message, profile}) =>{
    return async (dispatch)=>{
        if(message.trim() == ''){
            return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: message_empty });  
        }
        try {
            dispatch({type: WAIT_FOR_MESSAGE})
            const request = {apiKey, message, target: profile};
            const res = await API.post(APIPaths.sendMessage,request);
            res
                ? checkSendMessageResponse(res.data, dispatch)
                : dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
        }
    } 
};

const checkSendMessageResponse = (data, dispatch) => {
    const {reason} = data
    switch (reason) {
      case Empty:
        return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: message_empty }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: user_not_exist });
      case Success:
        alert(message_sent)
        return dispatch({ type: MESSAGE_SENT});
      default:
        return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
    }
};

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_WATCH_PROFILE,
        payload: apiKey
    }
};
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { MessageErrors ,GeneralErrors } from '../locale/locale_heb';
import {
    RESET_WATCH_MESSAGES,
    SEARCH_WATCH_MESSAGES_CHANGED,
    PRESENTED_MESSAGES_CHANGED,
    CHOOSE_MESSAGE,
    MESSAGE_DELETED,
    UPDATE_ERROR_WATCH_MESSAGES,
    WAIT_FOR_DELETE_MESSAGE,
} from "./types"

import {
    Success,
    Not_Exist,
    Message_Not_Exist,
  } from './OpCodeTypes';

const {
    user_not_exist,
    server_error,
} = GeneralErrors;

const {
    message_not_found,
    message_deleted,
} = MessageErrors;

export const searchChanged= (text) => {
    return {
        type: SEARCH_WATCH_MESSAGES_CHANGED,
        payload: text,
    };
};

export const filterMessages = (search, messages) =>{
    return {
        type: PRESENTED_MESSAGES_CHANGED,
        payload: messages.filter((message)=> message.writer.includes(search))
    }
};

export const messageChanged = (message) => {
    return {
        type: CHOOSE_MESSAGE,
        payload: message,
    }
};

export const deleteMessage = ({apiKey, message}) => {
    return async (dispatch)=>{
        try {
            dispatch({type: WAIT_FOR_DELETE_MESSAGE})
            const request = {apiKey, id: message.id};
            const res = await API.post(APIPaths.deleteMessage,request);
            res
                ? checkDeleteMessageResponse(res.data, dispatch, message)
                : dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
        }
    } 
}

const checkDeleteMessageResponse = (data, dispatch, message) => {
    const {reason} = data
    switch (reason) {
      case Message_Not_Exist:
        return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: message_not_found }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: user_not_exist });
      case Success:
        alert(message_deleted)
        return dispatch({ type: MESSAGE_DELETED, payload:message.id});
      default:
        return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
    }
}

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_WATCH_MESSAGES,
        payload: apiKey
    }
}
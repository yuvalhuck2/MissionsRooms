import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { WatchSuggestionsStrings ,GeneralErrors } from '../locale/locale_heb';
import {
    CHOOSE_SUGGESTION,
    RESET_WATCH_SUGGESTIONS,
    SUGGESTION_DELETED,
    UPDATE_ERROR_WATCH_SUGGESTIONS,
    WAIT_FOR_DELETE_SUGGESTION,

} from '../actions/types';

import {
    Success,
    Not_Exist,
  } from './OpCodeTypes';

const {
    server_error,
    teacher_not_exists_error,
} = GeneralErrors;

const {
    suggestion_deleted
} = WatchSuggestionsStrings;

export const suggestionChanged = (suggestion) => {
    return {
        type: CHOOSE_SUGGESTION,
        payload: suggestion,
    }
} 

export const deleteSuggestion = ({apiKey, suggestion}) => {
    return async (dispatch)=>{
        try {
            dispatch({type: WAIT_FOR_DELETE_SUGGESTION})
            const request = {apiKey, id: suggestion.id};
            const res = await API.post(APIPaths.deleteSuggestion,request);
            res
                ? checkDeleteSuggestionResponse(res.data, dispatch, suggestion)
                : dispatch({ type: UPDATE_ERROR_WATCH_SUGGESTIONS, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_WATCH_SUGGESTIONS, payload: server_error });
        }
    } 
}

const checkDeleteSuggestionResponse = (data, dispatch, suggestion) => {
    const {reason} = data
    console.log(data)
    switch (reason) {
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_WATCH_SUGGESTIONS, payload: teacher_not_exists_error });
      case Success:
        alert(suggestion_deleted)
        return dispatch({ type: SUGGESTION_DELETED, payload:suggestion.id});
      default:
        return dispatch({ type: UPDATE_ERROR_WATCH_SUGGESTIONS, payload: server_error });
    }
}

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_WATCH_SUGGESTIONS,
        payload: apiKey
    }
}
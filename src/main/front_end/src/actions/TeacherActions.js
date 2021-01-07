import API from '../api/API';
import { GeneralErrors } from '../locale/locale_heb';
import * as APIPaths from '../api/APIPaths'
import * as NavPaths from '../navigation/NavPaths'

import {
    UPDATE_ERROR_TEMPLATE,
    UPDATE_ERROR_ROOM,
    GET_MISSIONS,
    GET_TEMPLATES,
    LOGIN_TEACHER,
  } from '../actions/types';

  import {
    Success,
  } from './OpCodeTypes';

  const {
    server_error,
  }=GeneralErrors

export const passToAddTemplate=({navigation,apiKey})=>{
    
    return async (dispatch)=> {
      dispatch({ type: LOGIN_TEACHER,payload:apiKey});
      navigation.navigate(NavPaths.AddRoom);
        try {
            const res = await API.post(APIPaths.searchMission, {apiKey});
            res ? checkSearchMissionResponse(res.data, dispatch)
            : dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
          } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
          }
        
    }
}

const checkSearchMissionResponse = (data,dispatch) =>{
    const {reason,value} =data
    switch (reason) {
          case Success:
            return dispatch({ type: GET_MISSIONS, payload: value });
          default:
            return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
      }
}

export const passToAddRoom=({navigation,apiKey})=>{
    
  return async (dispatch)=> {
    dispatch({ type: LOGIN_TEACHER,payload:apiKey});
    navigation.navigate(NavPaths.AddRoom);
      try {
          const res = await API.post(APIPaths.searchTemplate, {apiKey});
          console.log(res.data)
          console.log(apiKey)
          res ? checkSearchTemplatesResponse(res.data, dispatch)
          : dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
        }
      
  }
}

const checkSearchTemplatesResponse = (data,dispatch) =>{
  const {reason,value} =data
  switch (reason) {
        case Success:
          return dispatch({ type: GET_TEMPLATES, payload: value });
        default:
          return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
}
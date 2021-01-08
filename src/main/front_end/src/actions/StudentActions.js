import API from '../api/API';
import { GeneralErrors } from '../locale/locale_heb';
import * as APIPaths from '../api/APIPaths'
import * as NavPaths from '../navigation/NavPaths'

import {
    LOGIN_STUDENT,
    UPDATE_ERROR_SOLVE_ROOM,
    GET_STUDENT_ROOMS,
  } from '../actions/types';

  import {
    Success,
  } from './OpCodeTypes';

  const {
    server_error,
  }=GeneralErrors


export const passToMyRooms=({navigation,apiKey})=>{
    
  return async (dispatch)=> {
    dispatch({ type: LOGIN_STUDENT,payload:apiKey});
    try {
      const res = await API.post(APIPaths.watchStudetRooms, {apiKey});
      if(res) {
        checkGetStudentRoomsResponse(res.data, dispatch,navigation)
      }
      else{
        dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
    }
      
  }
}

const checkGetStudentRoomsResponse = (data,dispatch,navigation) =>{
  const {reason,value} =data
  switch (reason) {
        case Success:
          dispatch({ type: GET_STUDENT_ROOMS, payload: value });
          return navigation.navigate(NavPaths.chooseStudentRoom);
        default:
          return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
    }
}
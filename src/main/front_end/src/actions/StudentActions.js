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


export const passToMyRooms=({navigation,apiKey,rooms})=>{
    
  return async (dispatch)=> {
    dispatch({ type: LOGIN_STUDENT,payload:apiKey});
    try {
      const res = await API.post(APIPaths.watchStudetRooms, {apiKey});
      if(res) {
        checkGetStudentRoomsResponse({data:res.data, dispatch,navigation,rooms})
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

const checkGetStudentRoomsResponse = ({data,dispatch,navigation,rooms}) =>{
  const {reason,value} =data
  switch (reason) {
        case Success:
          rooms=new Map(value.map((newRoom)=> rooms.has(newRoom.roomId) ? [newRoom.roomId,rooms.get(newRoom.roomId)]:[newRoom.roomId,newRoom]));
          dispatch({ type: GET_STUDENT_ROOMS, payload: rooms });
          return navigation.navigate(NavPaths.chooseStudentRoom);
        default:
          return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
    }
}
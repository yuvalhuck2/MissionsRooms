import {
  GET_STUDENT_ROOMS,
  LOGIN_STUDENT,
  UPDATE_ERROR_SOLVE_DETERMINISTIC,
  Student_Not_Exist_In_Class,
  Student_Not_Exist_In_Group,
  Wrong_Mission,
  STUDENT_DIALOG,
} from '../actions/types';
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors, passToMyRoomsErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import { Success, Wrong_Key } from './OpCodeTypes';

const { server_error } = GeneralErrors;

const {
  student_not_exist_in_class_error,
  student_not_exist_in_group_error,
  wrong_mission_error,
  wrong_key_error,
} = passToMyRoomsErrors;

export const passToMyRooms = ({ navigation, apiKey, rooms }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_STUDENT, payload: apiKey });
    try {
      const res = await API.post(APIPaths.watchStudetRooms, { apiKey });
      if (res) {
        checkGetStudentRoomsResponse({
          data: res.data,
          dispatch,
          navigation,
          rooms,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
    }
  };
};

const checkGetStudentRoomsResponse = ({
  data,
  dispatch,
  navigation,
  rooms,
}) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: wrong_key_error });
    case Student_Not_Exist_In_Class:
      return dispatch({
        type: UPDATE_ERROR_SOLVE_DETERMINISTIC,
        payload: student_not_exist_in_class_error,
      });
    case Student_Not_Exist_In_Group:
      return dispatch({
        type: UPDATE_ERROR_SOLVE_DETERMINISTIC,
        payload: student_not_exist_in_group_error,
      });
    case Wrong_Mission:
      return dispatch({
        type: UPDATE_ERROR_SOLVE_DETERMINISTIC,
        payload: wrong_mission_error,
      });
    case Success:
      rooms = new Map(
        value.map((newRoom) =>
          rooms.has(newRoom.roomId)
            ? [newRoom.roomId, rooms.get(newRoom.roomId)]
            : [newRoom.roomId, newRoom]
        )
      );
      dispatch({ type: GET_STUDENT_ROOMS, payload: rooms });
      return navigation.navigate(NavPaths.chooseStudentRoom);
    default:
      return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
  }
};

export const changeDialog = (content)=>{
  return async (dispatch)=>{
    dispatch({type:STUDENT_DIALOG, payload:content})
  }
};

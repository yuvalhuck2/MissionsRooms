import {
    CLEAR_STATE,
    GET_CLASSROOM,
    GET_MISSIONS, GET_STUDENT_ROOMS,
    GET_TEMPLATES, LOGIN_STUDENT,
    LOGIN_TEACHER,
    UPDATE_ERROR_ROOM, UPDATE_ERROR_SOLVE_DETERMINISTIC,
    UPDATE_ERROR_TEMPLATE,
    UPDATE_ERROR_TEACHER_ROOMS, CURRENT_ROOM_CHANGED,PASS_TEACHER_ROOM_ERROR,GET_TEACHER_ROOMS_TYPE
} from '../actions/types';
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import { Not_Exist, Success, Wrong_Key,Teacher_Classroom_Is_Null,DB_Error,Not_Exist_Room } from './OpCodeTypes';
import {closeSocket} from '../handler/WebSocketHandler'
import {Student_Not_Exist_In_Class, Student_Not_Exist_In_Group, Wrong_Mission} from "./types";

const {
  server_error,
  wrong_key_error,
  teacher_not_exists_error, not_exist_room_error,teacher_classroom_is_null_error,db_error
} = GeneralErrors;

export const passToAddTemplate = ({ navigation, apiKey }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    navigation.navigate(NavPaths.addTemplate);
    try {
      const res = await API.post(APIPaths.searchMission, { apiKey });
      res
        ? checkSearchMissionResponse(res.data, dispatch)
        : dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
    }
  };
};

const checkSearchMissionResponse = (data, dispatch) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      return dispatch({ type: GET_MISSIONS, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
  }
};


export const passToRooms = ({ navigation, apiKey, roomsType }) => {
    return async (dispatch) => {
        dispatch({ type: LOGIN_TEACHER, payload: apiKey });
        try {
            const res = await API.post(APIPaths.watchTeacherRooms, { apiKey });
            if (res) {
                checkGetSTeacherRoomsResponse({
                    data: res.data,
                    dispatch,
                    navigation,
                    roomsType,
                });
            } else {
                dispatch({ type: PASS_TEACHER_ROOM_ERROR, payload: server_error });
            }
        } catch (err) {
            console.log(err);
            return dispatch({ type: PASS_TEACHER_ROOM_ERROR, payload: server_error });
        }
    };
};

const checkGetSTeacherRoomsResponse = ({
                                          data,
                                          dispatch,
                                          navigation,
                                           roomsType,
                                      }) => {
    const { reason, value } = data;
    switch (reason) {
        case Wrong_Key:
            return dispatch({ type:UPDATE_ERROR_TEACHER_ROOMS , payload: wrong_key_error });
        case Teacher_Classroom_Is_Null:
            return dispatch({
                type: UPDATE_ERROR_TEACHER_ROOMS,
                payload: teacher_classroom_is_null_error,
            });
        case DB_Error:
            return dispatch({
                type: UPDATE_ERROR_TEACHER_ROOMS,
                payload: db_error,
            });
        case Not_Exist_Room:
            return dispatch({
                type: UPDATE_ERROR_TEACHER_ROOMS,
                payload: not_exist_room_error,
            });
        case Success:
            roomsType = value;
            /*new Map(
                value.map((roomType) => [roomType.roomType, roomType])
            );*/
            dispatch({ type: GET_TEACHER_ROOMS_TYPE, payload: roomsType });
            return navigation.navigate(NavPaths.chooseTeacherRoomType);
        default:
            return dispatch({ type: UPDATE_ERROR_TEACHER_ROOMS, payload: server_error });
    }
};

export const passToAddRoom = ({ navigation, apiKey }) => {
  return async (dispatch) => {
    dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    try {
      const res = await API.post(APIPaths.getClassroom, { apiKey });
      if (res) {
        checkGetClassroomResponse(res.data, dispatch, navigation);
      } else {
        dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
    try {
      const res = await API.post(APIPaths.searchTemplate, { apiKey });
      res
        ? checkSearchTemplatesResponse(res.data, dispatch)
        : dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
  };
};

const checkGetClassroomResponse = (data, dispatch, navigation) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      dispatch({ type: GET_CLASSROOM, payload: value });
      return navigation.navigate(NavPaths.AddRoom);
    default:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
  }
};

const checkSearchTemplatesResponse = (data, dispatch) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Success:
      return dispatch({ type: GET_TEMPLATES, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
  }
};

export const logout = (navigation) => {
  return async (dispatch) => {
    dispatch({ type: CLEAR_STATE });
    closeSocket();
    return navigation.navigate(NavPaths.loginScreen);
  };
};

export const roomChanged = (room) => {
    return {
        type: CURRENT_ROOM_CHANGED,
        payload: room.roomId,
    };
};

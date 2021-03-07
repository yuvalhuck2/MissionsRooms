import {
    GET_STUDENT_ROOMS,
    LOGIN_STUDENT,
    UPDATE_ERROR_SOLVE_ROOM,
    Student_Not_Exist_In_Class,
    Student_Not_Exist_In_Group,
    Wrong_Mission, UPDATE_ERROR,
    SUGGESTION_CHANGED,
    ADD_SUGGESTION,
    CLEAR_STATE,
  UPDATE_ERROR_SOLVE_DETERMINISTIC,
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
    Wrong_Suggestion,wrong_key,
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

export const passToAddSuggestion = ({ navigation, apiKey}) => {
    return async (dispatch) => {
        dispatch({ type: LOGIN_STUDENT, payload: apiKey  });
        return navigation.navigate(NavPaths.addSuggestion);
    };
};


export const addSuggestion = ({navigation, apiKey,suggestion})=>{
  return async (dispatch)=>{
    dispatch({type:LOGIN_STUDENT,payload:apiKey});
    try{
      const  res= await API.post(APIPaths.addSuggestion,{apiKey, suggestion});
      if(res){
          checkAddSuggestionResponse({
              data:res.data,
              dispatch,
              navigation,
          })
      } else{
        dispatch({type:UPDATE_ERROR,payload:server_error});
      }
    }catch (e) {
      console.log(e);
        return dispatch({type:UPDATE_ERROR,payload:server_error});

    }
  };
};

const checkAddSuggestionResponse = ({
  data,
  dispatch,
  navigation,
}) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_key });
    case Student_Not_Exist_In_Class:
      return dispatch({
        type: UPDATE_ERROR,
        payload: student_not_exist_in_class_error,
      });
    case Student_Not_Exist_In_Group:
      return dispatch({
        type: UPDATE_ERROR,
        payload: student_not_exist_in_group_error,
      });
    case Wrong_Suggestion:
      return dispatch({
        type: UPDATE_ERROR,
        payload: Wrong_Suggestion,
      });
    case Success:
      navigation.navigate(NavPaths.studentMainScreen);
      return dispatch({ type: ADD_SUGGESTION, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};

export const suggestionChange = (text) => {
    return {
        type: SUGGESTION_CHANGED,
        payload: text,
    };
};

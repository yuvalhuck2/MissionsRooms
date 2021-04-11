import {
    GET_STUDENT_ROOMS,
    LOGIN_STUDENT,
    UPDATE_ERROR_SOLVE_ROOM,
    Student_Not_Exist_In_Class,
    Student_Not_Exist_In_Group,
    Wrong_Mission, UPDATE_ERROR,
    SUGGESTION_CHANGED, ADD_SUGGESTION,
    CLEAR_STATE,
  UPDATE_ERROR_SOLVE_DETERMINISTIC,
  STUDENT_DIALOG,
  UPDATE_ERROR_WATCH_PROFILE,
  UPDATE_ALL_USER_PROFILES,
  UPDATE_ERROR_WATCH_MESSAGES,
  UPDATE_ALL_MESSAGES,
  RESET_POINTS_TABLE,
  UPDATE_ERROR_POINTS_TABLE,
  INIT_POINTS_TABLE,
  UPDATE_ERROR_ADD_SUGGESTION,
} from '../actions/types';
import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors, passToMyRoomsErrors, addSuggestionErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import { Success, Wrong_Key,Not_Exist, Wrong_Suggestion} from './OpCodeTypes';

const { server_error, wrong_key_error, } = GeneralErrors;

const {
  student_not_exist_in_class_error,
  student_not_exist_in_group_error,
  wrong_mission_error,
} = passToMyRoomsErrors;

const {
  wrong_suggestion,
  suggestion_added,
} = addSuggestionErrors;

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

export const passToWatchProfiles = ({navigation,apiKey}) => {
  return async (dispatch)=> {
    dispatch({ type: LOGIN_STUDENT, payload: apiKey });
    try {
      const res = await API.post(APIPaths.watchProfile, { apiKey });
      if (res) {
        checkGetProfilesResponse({
          data: res.data,
          dispatch,
          navigation,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
    }
  };
}

const checkGetProfilesResponse = ({data, dispatch, navigation,}) => {
  const { reason, value } = data;
  switch (reason) {
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_WATCH_PROFILE,
        payload: wrong_key_error,
      });
    case Success:
      dispatch({ type: UPDATE_ALL_USER_PROFILES, payload: value });
      return navigation.navigate(NavPaths.watchProfile);
    default:
      return dispatch({ type: UPDATE_ERROR_WATCH_PROFILE, payload: server_error });
  }
}

export const passToWatchMessages = ({navigation,apiKey}) => {
  return async (dispatch)=> {
    dispatch({ type: LOGIN_STUDENT, payload: apiKey });
    try {
      const res = await API.post(APIPaths.watchMessages, { apiKey });
      if (res) {
        checkWatchMessagesResponse({
          data: res.data,
          dispatch,
          navigation,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
    }
  };
}

const checkWatchMessagesResponse = ({data, dispatch, navigation,}) => {
  const { reason, value } = data;
  switch (reason) {
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_WATCH_MESSAGES,
        payload: wrong_key_error,
      });
    case Success:
      dispatch({ type: UPDATE_ALL_MESSAGES, payload: value });
      return navigation.navigate(NavPaths.watchMessages);
    default:
      return dispatch({ type: UPDATE_ERROR_WATCH_MESSAGES, payload: server_error });
  }
}

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
        dispatch({type:UPDATE_ERROR_ADD_SUGGESTION,payload:server_error});
      }
    }catch (e) {
      console.log(e);
        return dispatch({type:UPDATE_ERROR_ADD_SUGGESTION,payload:server_error});

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
      return dispatch({ type: UPDATE_ERROR_ADD_SUGGESTION, payload: wrong_key });
    case Student_Not_Exist_In_Class:
      return dispatch({
        type: UPDATE_ERROR_ADD_SUGGESTION,
        payload: student_not_exist_in_class_error,
      });
    case Student_Not_Exist_In_Group:
      return dispatch({
        type: UPDATE_ERROR_ADD_SUGGESTION,
        payload: student_not_exist_in_group_error,
      });
    case Wrong_Suggestion:
      return dispatch({
        type: UPDATE_ERROR_ADD_SUGGESTION,
        payload: wrong_suggestion,
      });
    case Success:
      alert(suggestion_added)
      navigation.navigate(NavPaths.studentMainScreen);
      return dispatch({ type: ADD_SUGGESTION, payload: value });
    default:
      return dispatch({ type: UPDATE_ERROR_ADD_SUGGESTION, payload: server_error });
  }
};

export const suggestionChange = (text) => {
    return {
        type: SUGGESTION_CHANGED,
        payload: text,
    };
};

export const passToWatchPointsTable = ({navigation,apiKey, isStudent}) => {
  return async (dispatch)=> {
    dispatch({ type: RESET_POINTS_TABLE, payload: apiKey });
    try {
      const res = await API.post(APIPaths.watchPointsTable, { apiKey });
      if (res) {
        checkWatchPointsTableResponse({
          data: res.data,
          dispatch,
          navigation,
          isStudent,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
    }
  };
}

const checkWatchPointsTableResponse = ({data, dispatch, navigation,isStudent}) => {
  const { reason, value } = data;
  switch (reason) {
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_POINTS_TABLE,
        payload: wrong_key_error,
      });
    case Success:
      dispatch({ type: INIT_POINTS_TABLE, payload: {... value, isStudent} });
      return navigation.navigate(NavPaths.watchPointsTable);
    default:
      return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
  }
}
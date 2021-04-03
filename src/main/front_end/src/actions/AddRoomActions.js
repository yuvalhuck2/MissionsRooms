import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import {
  AddRoomErrors,
  AddRoomResponseErrors,
  GeneralErrors,
} from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import {
  Already_Exist_Class,
  Already_Exist_Group,
  Already_Exist_Student,
  Not_Exist,
  Not_Exist_Classroom,
  Not_Exist_Group,
  Not_Exist_Student,
  Not_Exist_Template,
  Null_Error,
  Success,
  Type_Not_Match,
  Wrong_Bonus,
  Wrong_Key,
  Wrong_Name,
  Wrong_Type,
} from './OpCodeTypes';
import {
  ADD_ROOM,
  BONUS_CHANGED,
  CLASSROOM,
  FILTER_TEMPLATES,
  GROUP,
  GROUP_CHANGED,
  LOGIN_TEACHER,
  PASS,
  PERSONAL,
  ROOM_NAME_CHANGED,
  SEARCH_TEMPLATE_CHANGED,
  STUDENT_CHANGED,
  TEMPLATE_CHANGED,
  UPDATE_ERROR_ROOM,
} from './types';

const {
  name_empty,
  bonus_empty_negative,
  classroom_empty,
  group_empty,
  student_empty,
  empty_template,
  room_added,
} = AddRoomErrors;

const {
  server_error,
  wrong_key_error,
  teacher_not_exists_error,
} = GeneralErrors;

const {
  null_room_add_room_error,
  wrong_name_add_room_error,
  wrong_bonus_add_room_error,
  not_exist_template_add_room_error,
  type_not_match_add_room_error,
  wrong_type_add_room_error,
  not_exist_student_add_room_error,
  already_exist_student_add_room_error,
  not_exist_group_add_room_error,
  already_exist_group_add_room_error,
  not_exist_classroom_add_room_error,
  already_exist_class_add_room_error,
} = AddRoomResponseErrors;

export const nameChanged = (text) => {
  return {
    type: ROOM_NAME_CHANGED,
    payload: text,
  };
};

export const bonusChanged = (text) => {
  return {
    type: BONUS_CHANGED,
    payload: text,
  };
};

export const groupChanged = (text) => {
  return {
    type: GROUP_CHANGED,
    payload: text,
  };
};

export const studentChanged = (text) => {
  return {
    type: STUDENT_CHANGED,
    payload: text,
  };
};

export const templateChanged = (text) => {
  return {
    type: TEMPLATE_CHANGED,
    payload: text,
  };
};

export const passToTemplates = ({
  roomName,
  bonus,
  classroom,
  group,
  student,
  allTemplates,
  type,
  navigation,
}) => {
  return async (dispatch) => {
    if (roomName.trim() === '') {
      dispatch({ type: UPDATE_ERROR_ROOM, payload: name_empty });
    } else if (bonus == undefined || bonus < 0) {
      dispatch({ type: UPDATE_ERROR_ROOM, payload: bonus_empty_negative });
    } else {
      switch (type) {
        case CLASSROOM:
          payload = { participant: classroom, roomType: CLASSROOM };
          break;
        case GROUP:
          if (group.trim() === '') {
            return dispatch({ type: UPDATE_ERROR_ROOM, payload: group_empty });
          } else {
            payload = { participant: group, roomType: GROUP };
          }
          break;
        case PERSONAL:
          if (student.trim() === '') {
            return dispatch({
              type: UPDATE_ERROR_ROOM,
              payload: student_empty,
            });
          } else {
            payload = { participant: student, roomType: PERSONAL };
          }
          break;
        default:
          return dispatch({
            type: UPDATE_ERROR_ROOM,
            payload: classroom_empty,
          });
      }
      templates = allTemplates.filter((template) => template.type == type);
      dispatch({
        type: PASS,
        payload: { ...payload, presentedTemplates: templates },
      });
      navigation.navigate(NavPaths.ChooseTemplate);
    }
  };
};

export const addRoom = ({
  roomName,
  participantKey,
  roomTemplateId,
  bonus,
  apiKey,
  type,
  navigation,
}) => {
  return async (dispatch) => {
    if (roomTemplateId == undefined || roomTemplateId.trim() === '') {
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: empty_template });
    } else {
      try {
        dispatch({ type: ADD_ROOM, payload: '' });
        const room = {
          roomName,
          participantKey,
          roomTemplateId,
          bonus,
          roomType: type,
          apiKey,
        };
        const res = await API.post(APIPaths.addRoom, room);
        res
          ? checkAddRoomResponse(res.data, dispatch, navigation, apiKey)
          : dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
      } catch (err) {
        console.log(err);
        return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
      }
    }
  };
};

const checkAddRoomResponse = (data, dispatch, navigation, apiKey) => {
  const { reason } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: teacher_not_exists_error,
      });
    case Null_Error:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: null_room_add_room_error,
      });
    case Wrong_Name:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: wrong_name_add_room_error,
      });
    case Wrong_Bonus:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: wrong_bonus_add_room_error,
      });
    case Not_Exist_Template:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: not_exist_template_add_room_error,
      });
    case Type_Not_Match:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: type_not_match_add_room_error,
      });
    case Wrong_Type:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: wrong_type_add_room_error,
      });
    case Not_Exist_Student:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: not_exist_student_add_room_error,
      });
    case Already_Exist_Student:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: already_exist_student_add_room_error,
      });
    case Not_Exist_Group:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: not_exist_group_add_room_error,
      });
    case Already_Exist_Group:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: already_exist_group_add_room_error,
      });
    case Not_Exist_Classroom:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: not_exist_classroom_add_room_error,
      });
    case Already_Exist_Class:
      return dispatch({
        type: UPDATE_ERROR_ROOM,
        payload: already_exist_class_add_room_error,
      });

    case Success:
      navigation.navigate(NavPaths.teacherMainScreen);
      alert(room_added);
      return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    default:
      return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
  }
};

export const searchChanged = (search) => {
  return {
    type: SEARCH_TEMPLATE_CHANGED,
    payload: search }
  }

export const filterTemplates = ({NotFilteredTemplates, search}) => {
  return {
    type: FILTER_TEMPLATES,
    payload: NotFilteredTemplates.filter((template) => template.name.includes(search))
  }
}

import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import {
  AddRoomTempalteStrings,
  addRoomTemplateErrors,
  AddTemplateErrors,
  GeneralErrors,
} from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import {
  Not_Exist,
  Not_Mission,
  Success,
  Too_Big_Amount,
  Type_Not_Match,
  Wrong_Amount,
  Wrong_Key,
  Wrong_List,
  Wrong_Name,
} from './OpCodeTypes';
import {
  ADD_TEMPLATE,
  LOGIN_TEACHER,
  MINIMAL_MISSIONS_CHANGED,
  MISSIONS_CHANGED,
  PASS_TO_CHOOSE_MISSIONS,
  TEMPLATE_NAME_CHANGED,
  TYPE_CHANGED,
  UPDATE_ERROR_TEMPLATE,
} from './types';

const {
  name_empty,
  minimal_negative,
  missions_empty,
  missions_to_small,
  type_empty,
} = AddTemplateErrors;

const { template_added } = AddRoomTempalteStrings;

const { server_error, teacher_not_exists_error } = GeneralErrors;

const {
  wrong_name_room_template_error,
  wrong_amount_room_template_error,
  big_amount_room_template_error,
  wrong_list_room_template_error,
  not_mission_room_template_error,
  type_not_match_room_template_error,
} = addRoomTemplateErrors;

export const nameChanged = (text) => {
  return {
    type: TEMPLATE_NAME_CHANGED,
    payload: text,
  };
};

export const minimalMissionsChanged = (text) => {
  return {
    type: MINIMAL_MISSIONS_CHANGED,
    payload: text,
  };
};

export const typeChanged = (text) => {
  return {
    type: TYPE_CHANGED,
    payload: text,
  };
};

export const missionsChanged = (list) => {
  return {
    type: MISSIONS_CHANGED,
    payload: list,
  };
};

export const passToMissions = ({
  name,
  minimalMissionsToPass,
  type,
  allMissions,
  navigation,
}) => {
  return async (dispatch) => {
    if (name.trim() === '') {
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: name_empty });
    } else if (type.trim() === '') {
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: type_empty });
    } else if (
      minimalMissionsToPass == undefined ||
      minimalMissionsToPass < 0
    ) {
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        payload: minimal_negative,
      });
    } else {
      dispatch({
        type: PASS_TO_CHOOSE_MISSIONS,
        payload: allMissions.filter((mis) => mis.missionTypes.includes(type)),
      });
      return navigation.navigate(NavPaths.ChooseMissionsForTemplate);
    }
  };
};

export const addTemplate = ({
  name,
  minimalMissionsToPass,
  missionsToAdd,
  type,
  apiKey,
  navigation,
}) => {
  return async (dispatch) => {
    if (missionsToAdd.length == 0) {
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: missions_empty });
    } else if (missionsToAdd.length < minimalMissionsToPass) {
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        payload: missions_to_small,
      });
    } else {
      try {
        dispatch({ type: ADD_TEMPLATE });
        const roomTemplate = {
          name,
          minimalMissionsToPass,
          missions: missionsToAdd.map((mis) => mis.missionId),
          type,
          apiKey,
        };
        const res = await API.post(APIPaths.addTemplate, roomTemplate);
        res
          ? checkAddTemplateResponse(res.data, dispatch, navigation, apiKey)
          : dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
      } catch (err) {
        console.log(err);
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
      }
    }
  };
};

const checkAddTemplateResponse = (data, dispatch, navigation, apiKey) => {
  const { reason } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, wrong_key_error });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        teacher_not_exists_error,
      });
    case Wrong_Name:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        wrong_name_room_template_error,
      });
    case Wrong_Amount:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        wrong_amount_room_template_error,
      });
    case Too_Big_Amount:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        big_amount_room_template_error,
      });
    case Wrong_List:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        wrong_list_room_template_error,
      });
    case Not_Mission:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        not_mission_room_template_error,
      });
    case Type_Not_Match:
      return dispatch({
        type: UPDATE_ERROR_TEMPLATE,
        type_not_match_room_template_error,
      });
    case Success:
      navigation.navigate(NavPaths.teacherMainScreen);
      alert(template_added);
      return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
    default:
      return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
  }
};

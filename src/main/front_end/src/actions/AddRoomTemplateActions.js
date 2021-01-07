import { AddTemplateErrors,AddRoomTempalteStrings,GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import * as APIPaths from '../api/APIPaths';
import API from '../api/API';
import {
    TEMPLATE_NAME_CHANGED,
    MINIMAL_MISSIONS_CHANGED,
    TYPE_CHANGED,
    MISSIONS_CHANGED,
    PASS_TO_CHOOSE_MISSIONS,
    ADD_TEMPLATE,
    UPDATE_ERROR_TEMPLATE,
    LOGIN_TEACHER,
  } from './types';
 
  import {
    Success,
  } from './OpCodeTypes';

const {
    name_empty,
    minimal_negative,
    missions_empty,
    missions_to_small,
    type_empty,
  } = AddTemplateErrors;

  const {
    template_added,
  }=AddRoomTempalteStrings;

  const {
    server_error,
  }=GeneralErrors

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

  export const passToMissions= ({name,minimalMissionsToPass,type,allMissions,navigation}) => {
    return async (dispatch)=>{
      if(name.trim()===""){
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: name_empty });
      }
      else if(type.trim()===""){
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: type_empty });
      }
      else if(minimalMissionsToPass==undefined || minimalMissionsToPass<0){
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: minimal_negative });
      }
      else{
        dispatch({type:PASS_TO_CHOOSE_MISSIONS, payload:allMissions.filter((mis)=>mis.missionTypes.includes(type))});
        return navigation.navigate(NavPaths.ChooseMissionsForTemplate);
      }
    }
  };

  export const addTemplate = ({name,minimalMissionsToPass,missionsToAdd,type,apiKey,navigation}) => {
    return async (dispatch)=>{
      if(missionsToAdd.length==0){
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: missions_empty });
      }
      else if(missionsToAdd.length<minimalMissionsToPass){
        return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: missions_to_small });
      }
      else{
        try {
          dispatch({ type: ADD_TEMPLATE });
          const roomTemplate={name,minimalMissionsToPass,missions: missionsToAdd.map((mis)=>mis.missionId),type,apiKey};
          const res = await API.post(APIPaths.addTemplate,roomTemplate);
          res
            ? checkAddTemplateResponse(res.data, dispatch, navigation,apiKey)
            : dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
        }
      }
    }
  };

  const checkAddTemplateResponse= (data,dispatch,navigation,apiKey) =>{
    const {reason,value} =data
    switch (reason) {
      // case Wrong_Password:
      //   return dispatch({ type: UPDATE_ERROR, payload: wrong_password_login });
      // case Wrong_Alias:
      //   return dispatch({ type: UPDATE_ERROR, payload: wrong_alias });
      // case Not_Exist:
      //   return dispatch({ type: UPDATE_ERROR, payload: not_exist });
      // case Supervisor:
      //   navigation.navigate(NavPaths.supMainScreen);
      //   return dispatch({ type: LOGIN_SUPERVISOR, payload: value });
        case Success:
          navigation.navigate(NavPaths.teacherMainScreen);
          alert(template_added)
          return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
        default:
          return dispatch({ type: UPDATE_ERROR_TEMPLATE, payload: server_error });
    }
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
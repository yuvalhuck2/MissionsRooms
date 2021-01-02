import { AddTemplateErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import {
    NAME_CHANGED,
    MINIMAL_MISSIONS_CHANGED,
    TYPE_CHANGED,
    MISSIONS_CHANGED,
    PASS,
    ADD_TEMPLATE,
    UPDATE_ERROR,
  } from './types';
 
const {
    name_empty,
    minimal_negative,
    missions_empty,
    missions_to_small,
    type_empty,
  } = AddTemplateErrors;

export const nameChanged = (text) => {
    return {
      type: NAME_CHANGED,
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
    console.log('action')
    return {
      type: MISSIONS_CHANGED,
      payload: list,
    };
  };

  export const passToMissions= (name,minimalMissions,type,navigation) => {
    return async (dispatch)=>{
      if(name.trim()===""){
        dispatch({ type: UPDATE_ERROR, payload: name_empty });
      }
      if(type.trim()===""){
        dispatch({ type: UPDATE_ERROR, payload: type_empty });
      }
      else if(minimalMissions==undefined || minimalMissions<0){
        dispatch({ type: UPDATE_ERROR, payload: minimal_negative });
      }
      else{
        navigation.navigate(NavPaths.ChooseMissionsForTemplate);
      }
    }
  };

  export const addTemplate = ({name,minimalMissions,missionsToAdd,type}) => {
    return async (dispatch)=>{
      if(missionsToAdd.length==0){
        dispatch({ type: UPDATE_ERROR, payload: missions_empty });
      }
      else if(missionsToAdd.length<minimalMissions){
        dispatch({ type: UPDATE_ERROR, payload: missions_to_small });
      }
      else{
        sendTemplate({name,minimalMissions,missionsToAdd,type},dispatch)
      }
    }
  };

  const sendTemplate= (template,dispatch) =>{
    dispatch({ type: ADD_TEMPLATE });
    alert(template)
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
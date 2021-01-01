import { AddTemplateErrors } from '../locale/locale_heb';
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
    return {
      type: MISSIONS_CHANGED,
      payload: list,
    };
  };

  export const passToMissions= (name,minimalMissions,type) => {
      console.log(name)
    return async (dispatch)=>{
      if(name.trim()===""){
        dispatch({ type: UPDATE_ERROR, payload: name_empty });
      }
      if(type.trim()===""){
        dispatch({ type: UPDATE_ERROR, payload: type_empty });
      }
      else if(minimalMissions<0){
        dispatch({ type: UPDATE_ERROR, payload: minimal_negative });
      }
      else{
        dispatch({ type: PASS })
      }
    }
  };

  export const addTemplate = (name,minimalMissions,missions,type) => {
    return async (dispatch)=>{
      if(missionTypes.length==0){
        dispatch({ type: UPDATE_ERROR, payload: missions_empty });
      }
      else{
        sendTemplate({name,minimalMissions,missions,type},dispatch)
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
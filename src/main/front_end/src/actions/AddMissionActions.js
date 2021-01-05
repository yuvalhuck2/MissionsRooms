import { AddDeterministicMissionErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'

import {
    QUESTION_CHANGED,
    ANSWER_CHANGED,
    TYPES_CHANGED,
    ADD_MISSON,
    DETERMINISTIC,
    UPDATE_ERROR_MISSION,
  } from '../actions/types';
  
  const {
    question_empty,
    answer_empty,
    types_empty,
  } = AddDeterministicMissionErrors;

export const questionChanged = (text) => {
    return {
      type: QUESTION_CHANGED,
      payload: text,
    };
  };
  
  export const answerChanged = (text) => {
    return {
      type: ANSWER_CHANGED,
      payload: text,
    };
  };

  export const typesChanged = (list) => {
    return {
      type: TYPES_CHANGED,
      payload: list,
    };
  };

  export const navigateToMission = (type,navigation) => {
    return async ()=>{
      switch(type){
        case DETERMINISTIC:
          navigation.navigate(NavPaths.AddDeterministicMission)
      }
  }

  };
  
  export const addMission = (question,realAnswer,missionTypes) => {
    return async (dispatch)=>{
      if(question.trim()===""){
        dispatch({ type: UPDATE_ERROR_MISSION, payload: question_empty });
      }
      else if(realAnswer.trim()===""){
        dispatch({ type: UPDATE_ERROR_MISSION, payload: answer_empty });
      }
      else if(missionTypes.length==0){
        dispatch({ type: UPDATE_ERROR_MISSION, payload: types_empty });
      }
      else{
        sendMission({CLASSNAME:DETERMINISTIC,
          DATA:{question,realAnswer,missionTypes}},dispatch)
      }
    }
  };

  const sendMission= (mission,dispatch) =>{
    dispatch({ type: ADD_MISSON });
    alert(mission)
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
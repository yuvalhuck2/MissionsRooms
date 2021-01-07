import API from '../api/API';
import { AddDeterministicMissionErrors,AddDeterministicMissionSuccess } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import * as APIPaths from '../api/APIPaths';

import {
    QUESTION_CHANGED,
    ANSWER_CHANGED,
    TYPES_CHANGED,
    ADD_MISSON,
    LOGIN_TEACHER,
    DETERMINISTIC,
    UPDATE_ERROR_MISSION,
  } from '../actions/types';

  import {
    Success,
  } from './OpCodeTypes';
  
  const {
    question_empty,
    answer_empty,
    types_empty,
    server_error,
  } = AddDeterministicMissionErrors;

  const {
    mission_added,
  }=AddDeterministicMissionSuccess

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
  
  export const addMission = ({apiKey,question,realAnswer,missionTypes,navigation}) => {
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
        try {
          dispatch({ type: ADD_MISSON });
          missionData=JSON.stringify({CLASSNAME:DETERMINISTIC,DATA:{question,realAnswer,missionTypes}});
          const res = await API.post(APIPaths.addMission, { apiKey, missionData });
          res
            ? checkAddMissionResponse(res.data, dispatch, navigation,apiKey)
            : dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
        } catch (err) {
          console.log(err);
          dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
        }
      }
    }
  };

  const checkAddMissionResponse= (data,dispatch,navigation,apiKey) =>{
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
          alert(mission_added)
          return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
        default:
          return dispatch({ type: UPDATE_ERROR_MISSION, payload: server_error });
    }
  }
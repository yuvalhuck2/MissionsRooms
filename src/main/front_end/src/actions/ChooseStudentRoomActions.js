import { ChooseRoomStudentErrors,GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import {
    CURRENT_ROOM_CHANGED,
    CURRENT_ANSWER_CHANGED,
    SOLVE_MISSION,
    UPDATE_ERROR_ROOM,
    PASS_TO_SOLVE_MISSIONS,
    UPDATE_ERROR_SOLVE_ROOM,
    SOLVE_MISSION_SEND,
    DETERMINISTIC_NAME,
    TRIES,
    LOGIN_STUDENT,
  } from './types';
 
  import {
    Success,
    Final,
  } from './OpCodeTypes';
const {
    room_empty,
    wrong_answer,
    fail,
    pass,
    final,
  } = ChooseRoomStudentErrors;

const {
    server_error,
  }=GeneralErrors

  export const roomChanged = (text) => {
    return {
      type: CURRENT_ROOM_CHANGED,
      payload: text,
    };
  };

  export const answerChanged= (currentMission,text) =>{
    return {
      type: CURRENT_ANSWER_CHANGED,
      payload: {...currentMission, currentAnswer:text},
    };
  }

  export const passToSolveMission= ({currentRoom,navigation}) => {
    return async (dispatch)=>{
      if(currentRoom==undefined){
        dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: room_empty });
      }
      else{
        currentMission=currentRoom.currentMission
        moveToSpecificMission(currentMission,dispatch,navigation)
      
      }
    }
  };

  const moveToSpecificMission=(currentMission,dispatch,navigation)=>{
    console.log(currentMission)
      switch(currentMission.name){
        case DETERMINISTIC_NAME:
          tries=getTriesFromMission(currentMission)
          dispatch({ type: PASS_TO_SOLVE_MISSIONS, payload: {...currentMission, tries, currentAnswer:''} });
          navigation.navigate(NavPaths.deterministicScreen);
          break;
        default:
            return ""
      }

  }

  const getTriesFromMission=(currentMission)=>{
    return currentMission.tries==undefined?
    TRIES
    :currentMission.tries
  }

  export const sendDeterministicAnswer = ({currentRoom,currentMission, navigation,apiKey}) => {
    return async (dispatch)=>{
      if(currentMission.answers[0].trim()==currentMission.currentAnswer.trim()){
        try {
          dispatch({ type: SOLVE_MISSION_SEND ,payload:''});
          const solution={roomId:currentRoom.roomId,answer:true,apiKey};
          const res = await API.post(APIPaths.solveDeterministic,solution);
          res
            ? checkSolveRespnose(res.data, dispatch, navigation,apiKey,pass)
            : dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
        }
      }
      else if(currentMission.tries==1){
        try {
          dispatch({ type: SOLVE_MISSION_SEND ,payload:''});
          const solution={roomId:currentRoom.roomId,answer:false,apiKey};
          const res = await API.post(APIPaths.solveDeterministic,solution);
          res
            ? checkSolveRespnose(res.data, dispatch, navigation,apiKey,fail)
            : dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
        }
      }
      else{//wrong so tries removed
        dispatch({type: CURRENT_ANSWER_CHANGED,payload:{... currentMission, tries:currentMission.tries-1}})
        return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: wrong_answer+(currentMission.tries-1) });
        
      }
    }
  };

  const checkSolveRespnose= (data,dispatch,navigation,apiKey,solution) =>{
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
          alert(solution)
          return dispatch({ type: LOGIN_STUDENT, payload: apiKey });
        case Final:
            navigation.navigate(NavPaths.studentMainScreen);
            alert(solution+final)
            return dispatch({ type: LOGIN_STUDENT, payload: apiKey });
        default:
          return dispatch({ type: UPDATE_ERROR_SOLVE_ROOM, payload: server_error });
    }
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
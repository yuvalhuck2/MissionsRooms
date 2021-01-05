import { ChooseRoomStudentErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import {
    CURRENT_ROOM_CHANGED,
    CURRENT_ANSWER_CHANGED,
    SOLVE_MISSION,
    UPDATE_ERROR_ROOM,
    PASS_TO_SOLVE_MISSIONS,
    UPDATE_ERROR_SOLVE_ROOM,
    DETERMINISTIC_NAME,
    TRIES,
  } from './types';
 
const {
    room_empty,
    wrong_answer,
  } = ChooseRoomStudentErrors;

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
      switch(currentMission.name){
        case DETERMINISTIC_NAME:
          dispatch({ type: PASS_TO_SOLVE_MISSIONS, payload: {...currentMission, tries:TRIES, currentAnswer:''} });
          navigation.navigate(NavPaths.deterministicScreen);
          break;
        default:
            return ""
      }

  }

  export const sendDeterministicAnswer = ({currentRoom,currentMission, navigation}) => {
    return async (dispatch)=>{
      if(currentMission.answer==currentMission.currentAnswer.trim()){
        sendRoom({currentRoom,whatHappend:'Success'},dispatch,navigation)
      }
      else if(currentMission.tries==1){
        sendRoom({currentRoom,whatHappend:'Fail'},dispatch,navigation)
      }
      else{//wrong so tries removed
        dispatch({type: CURRENT_ANSWER_CHANGED,payload:{... currentMission, tries:currentMission.tries-1}})
        dispatch({ type: UPDATE_ERROR_ROOM, payload: wrong_answer+(currentMission.tries-1) });
        
      }
    }
  };

  const sendRoom= ({currentRoom,whatHappend},dispatch,navigation) =>{
    dispatch({ type: SOLVE_MISSION });
    console.log(whatHappend)
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
import { AddRoomErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import {
    ROOM_NAME_CHANGED,
    BONUS_CHANGED,
    ADD_ROOM,
    PERSONAL,
    GROUP,
    PASS,
    CLASSROOM,
    TEMPLATE_CHANGED,
    UPDATE_ERROR_ROOM,
    GROUP_CHANGED,
    STUDENT_CHANGED,
    CLEAR_STATE,
  } from './types';
 
const {
    name_empty,
    bonus_empty_negative,
    classroom_empty,
    group_empty,
    student_empty,
    empty_template
  } = AddRoomErrors;

  export const nameChanged= (text)=>{
    return {
      type: ROOM_NAME_CHANGED,
      payload: text,
    };
  }
  
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

  export const passToTemplates= (roomName,bonus,classroom,group,student,allTemplates,type,navigation) => {
    return async (dispatch)=>{
      if(roomName.trim()===""){
        dispatch({ type: UPDATE_ERROR_ROOM, payload: name_empty });
      }
      else if(bonus==undefined || bonus<0){
        dispatch({ type: UPDATE_ERROR_ROOM, payload: bonus_empty_negative });
      }
      else{
        switch(type){
          case CLASSROOM:
            payload= {participant: classroom,roomType: CLASSROOM}
            break;
          case GROUP:
            if(group.trim()===""){
              return dispatch({ type: UPDATE_ERROR_ROOM, payload: group_empty });
            }
            else{
              payload={participant: group,roomType: GROUP}
            }
            break;
          case PERSONAL:
            if(student.trim()===""){
              return dispatch({ type: UPDATE_ERROR_ROOM, payload: student_empty });
            }
            else{
              payload= {participant: student,roomType: PERSONAL}
            }
            break;
          default:
            return dispatch({ type: UPDATE_ERROR_ROOM, payload: classroom_empty });
        }
        templates=allTemplates.filter((template)=>(template.type==type));
        dispatch({ type: PASS, payload:{...payload, templates:templates} });
        navigation.navigate(NavPaths.ChooseTemplate);
        
      }
    }
  };

  export const addRoom = ({roomName,participantKey,roomTemplateId,bonus,type,navigation}) => {
    return async (dispatch)=>{
      if(roomTemplateId==undefined||roomTemplateId.trim()===""){
        dispatch({ type: UPDATE_ERROR_ROOM, payload: empty_template });
      }
      else{
        sendRoom({roomName,participantKey,roomTemplateId,bonus,type},dispatch,navigation)
      }
    }
  };

  const sendRoom= (room,dispatch,navigation) =>{
    dispatch({ type: ADD_ROOM });
    console.log(room)
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
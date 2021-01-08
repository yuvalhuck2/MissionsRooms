import { AddRoomErrors,GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
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
    LOGIN_TEACHER,
    CLEAR_STATE,
  } from './types';

  
  import {
    Success,
  } from './OpCodeTypes';
 
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
  }=GeneralErrors


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

  export const passToTemplates= ({roomName,bonus,classroom,group,student,allTemplates,type,navigation}) => {
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
        dispatch({ type: PASS, payload:{...payload, presentedTemplates:templates} });
        navigation.navigate(NavPaths.ChooseTemplate);
        
      }
    }
  };

  export const addRoom = ({roomName,participantKey,roomTemplateId,bonus,apiKey,type,navigation}) => {
    return async (dispatch)=>{
      if(roomTemplateId==undefined||roomTemplateId.trim()===""){
         return dispatch({ type: UPDATE_ERROR_ROOM, payload: empty_template });
      }
      else{
        try {
          dispatch({ type: ADD_ROOM ,payload:''});
          const room={roomName,participantKey,roomTemplateId,bonus,roomType:type,apiKey};
          const res = await API.post(APIPaths.addRoom,room);
          res
            ? checkAddRoomResponse(res.data, dispatch, navigation,apiKey)
            : dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
        }
      }
    }
  };

  const checkAddRoomResponse= (data,dispatch,navigation,apiKey) =>{
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
          alert(room_added)
          return dispatch({ type: LOGIN_TEACHER, payload: apiKey });
        default:
          return dispatch({ type: UPDATE_ERROR_ROOM, payload: server_error });
    }
      //const res = await API.post('/UserAuth', { alias: email, password });
      // console.log(res.data);
      // if (res) {
      //   alert("wow")
      // }
  }
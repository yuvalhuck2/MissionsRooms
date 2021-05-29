import { ChooseRoomStudentErrors,GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import {
    CURRENT_ROOM_CHANGED,
    PASS_TO_SOLVE_MISSIONS,
    UPDATE_ERROR_CHOOSE_ROOM,
    DETERMINISTIC_NAME,
    STORY_NAME,
    INIT_DETEREMINISTIC,
    WAIT_FOR_ROOM_DATA,
    INIT_STORY_MISSION,
    EXIT_ROOM,
    OPEN_QUESTION_MISSION,
    INIT_OPEN_QUESTION_MISSION, ENTER_CHAT_ROOM_STUDENT,
    TRIVIA_NAME, 
    INIT_TRIVIA_MISSION,
} from './types';
 
  import {
    Wrong_Key,
    Not_Exist,
    IN_CHARGE,
    NOT_IN_CHARGE,
    Not_Exist_Room,
    NOT_BELONGS_TO_ROOM,
  } from './OpCodeTypes';
const {
    room_empty,
  } = ChooseRoomStudentErrors;

const {
    server_error,
    wrong_key_error,
    student_not_exist,
    room_error,
  }=GeneralErrors

  export const roomChanged = (room) => {
    return {
      type: CURRENT_ROOM_CHANGED,
      payload: room.roomId,
    };
  };

  export const passToSolveMission= ({currentRoom,navigation,apiKey}) => {
    
    return async (dispatch)=>{
      if(currentRoom==undefined){
        dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_empty });
      }
      else{
        try{
          dispatch({ type: WAIT_FOR_ROOM_DATA });
        const res = await API.post(APIPaths.watchRoomData,{apiKey,roomId:currentRoom.roomId});
          res
            ?  handleRoomData(res.data,dispatch,navigation)
            : dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: server_error });
        }catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: server_error });
        }
      }
    }
  };

 const handleRoomData=(data,dispatch,navigation)=>{
    const {reason,value} =data
    switch(reason){
      case Wrong_Key:
          return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: wrong_key_error });
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: student_not_exist });
      case NOT_BELONGS_TO_ROOM:
        return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_error });
      case Not_Exist_Room:
        return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_error });
      case IN_CHARGE:
        return moveToMission(value,dispatch,navigation,true)
      case NOT_IN_CHARGE:
        return moveToMission(value,dispatch,navigation,false)
    }
    alert("didn't handle room")
  }

  export const moveToMission= (roomData,dispatch,navigation,isInCharge)=>{
    switch(roomData.currentMission.name){
      case DETERMINISTIC_NAME:
        dispatch({ type: PASS_TO_SOLVE_MISSIONS});
        dispatch({ type: INIT_DETEREMINISTIC, payload: {roomData, isInCharge} })
        navigation.navigate(NavPaths.deterministicScreen);
        break;
      case STORY_NAME:
          dispatch({ type: PASS_TO_SOLVE_MISSIONS});
          dispatch({ type: INIT_STORY_MISSION, payload: {roomData, isInCharge} })
          navigation.navigate(NavPaths.storyScreen);
          break;
      case OPEN_QUESTION_MISSION:
        dispatch({ type: PASS_TO_SOLVE_MISSIONS});
        dispatch({ type: INIT_OPEN_QUESTION_MISSION, payload: {roomData, isInCharge} })
        navigation.navigate(NavPaths.SolveOpenQuestionMission);
        break;
      case TRIVIA_NAME:
        dispatch({ type: PASS_TO_SOLVE_MISSIONS});
        dispatch({ type: INIT_TRIVIA_MISSION, payload: {roomData, isInCharge}});
        navigation.navigate(NavPaths.solveTriviaMission);
        break;
      default:
        console.log(roomData)
        alert("didn't move from room")
    }
  }

  export const handleBack = ({navigation,apiKey,roomId,missionId})=>{
    return async (dispatch)=>{
      try{
        API.post(APIPaths.disconnectFromRoom,{apiKey,roomId});
        navigation.navigate(NavPaths.chooseStudentRoom)
        dispatch({type:EXIT_ROOM, payload:missionId})
      }
      catch(err) {
        console.log(err);
        alert(err)
      }
    }
  }
export const enterChatStudent = ({navigation})=> {
    return async (dispatch) => {
        dispatch({
            type: ENTER_CHAT_ROOM_STUDENT,
        });
        navigation.navigate(NavPaths.chatRoom);
    }
}
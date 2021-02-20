import { ChooseRoomStudentErrors,GeneralErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import {
    CURRENT_ANSWER_CHANGED,
    UPDATE_ERROR_SOLVE_DETERMINISTIC,
    TRIES,
    SOLVE_DETERMINISTIC_MISSION_SEND,
  } from './types';
 
  import {
    Success,
    Wrong_Key,
    Not_Exist,
  } from './OpCodeTypes';
const {
    wrong_answer,
    fail,
    pass,
  } = ChooseRoomStudentErrors;

const {
    server_error,
    wrong_key_error,
    student_not_exist,
  }=GeneralErrors

  export const answerChanged= (text) =>{
    return {
      type: CURRENT_ANSWER_CHANGED,
      payload: text,
    };
  }

  export const sendDeterministicAnswer = ({roomId,mission,tries,apiKey, navigation,currentAnswer }) => {

    return async (dispatch)=>{
      if(mission.answers[0].trim()==currentAnswer.trim()){
        try {
          dispatch({ type: SOLVE_DETERMINISTIC_MISSION_SEND ,payload:''});
          const solution={roomId,answer:true,apiKey};
          const res = await API.post(APIPaths.solveDeterministic,solution);
          res
            ? checkSolveRespnose(res.data, dispatch,pass)
            : dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
        }
      }
      else if(tries==1){
        try {
          dispatch({ type: SOLVE_DETERMINISTIC_MISSION_SEND ,payload:''});
          const solution={roomId,answer:false,apiKey};
          const res = await API.post(APIPaths.solveDeterministic,solution);
          res
            ? checkSolveRespnose(res.data, dispatch,fail)
            : dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
        } catch (err) {
          console.log(err);
          return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
        }
      }
      else{//wrong so tries removed
        dispatch({ type: TRIES,payload:tries-1});
        return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: wrong_answer+(tries-1) });
        
      }
    }
  };

  const checkSolveRespnose= (data,dispatch,solutionMessage) =>{
    const {reason,value} =data
    switch (reason) {
      case Wrong_Key:
        return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: wrong_key_error }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: student_not_exist });
      case Success:
        alert(solutionMessage)
        return dispatch({ type: TRIES, payload: 3 });
      default:
        return dispatch({ type: UPDATE_ERROR_SOLVE_DETERMINISTIC, payload: server_error });
    }
  }